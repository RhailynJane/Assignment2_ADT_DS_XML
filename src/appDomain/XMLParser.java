package appDomain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utilities.StackADT;
import utilities.QueueADT;
import utilities.Iterator;
import implementations.MyStack;
import implementations.MyQueue;

/**
 * XMLParser reads and validates XML files using custom stack and queue data structures.
 * It ensures that all opening tags have matching and properly nested closing tags.
 *
 * Implements a parsing logic inspired by Kitty's XML Parser Algorithm.
 *
 * @author Abel Fekadu, Annie Marie,
 * Komalpreet Kaur, Rhailyn Jane Cona, and Samuel Braun
 * @version 1.3
 */
public class XMLParser {

    private List<String> errors;            // Stores error messages
    private StackADT<String> tagStack;      // Stack to track open tags
    private QueueADT<String> errorQ;        // Queue to track tag errors (mismatches)
    private QueueADT<String> extrasQ;       // Queue to track extra unmatched tags
    private QueueADT<String> tagHistory;    // Queue to track all tags seen in order

    /**
     * Constructs a new XMLParser with empty stack, queues, and error list.
     */
    public XMLParser() {
        errors = new ArrayList<>();
        tagStack = new MyStack<>();
        errorQ = new MyQueue<>();
        extrasQ = new MyQueue<>();
        tagHistory = new MyQueue<>();
    }

    /**
     * Parses an XML file and validates the structure of its tags.
     *
     * @param filename path to the XML file
     * @return true if XML is well-formed; false otherwise
     * @throws IOException if file reading fails
     */
    public boolean parseFile(String filename) throws IOException {
        errors.clear();
        tagStack.clear();
        errorQ.dequeueAll();
        extrasQ.dequeueAll();
        tagHistory.dequeueAll();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                parseLine(line.trim(), lineNumber);
            }
        }

        // After processing all lines, push remaining open tags to errorQ
        while (!tagStack.isEmpty()) {
            String unclosedTag = tagStack.pop();
            errorQ.enqueue(unclosedTag);
        }

        // Match tags in errorQ and extrasQ as per Kitty's algorithm
        reconcileErrors();

        return errors.isEmpty();
    }

    /**
     * Parses a single line of XML and validates tags according to rules.
     *
     * @param line       the line of XML to parse
     * @param lineNumber the line number (for error reporting)
     */
    private void parseLine(String line, int lineNumber) {
        int index = 0;

        while (index < line.length()) {
            int openTagStart = line.indexOf('<', index);
            if (openTagStart == -1) break;

            int openTagEnd = line.indexOf('>', openTagStart);
            if (openTagEnd == -1) {
                errors.add("Line " + lineNumber + ": Tag not closed properly.");
                break;
            }

            String tagContent = line.substring(openTagStart + 1, openTagEnd).trim();

            // Ignore processing instructions and comments
            if (tagContent.startsWith("!--") || tagContent.startsWith("?")) {
                index = openTagEnd + 1;
                continue;
            }

            // Self-closing tag: <tag/>
            if (tagContent.endsWith("/")) {
                String selfTag = tagContent.split("\\s+")[0];
                tagHistory.enqueue(selfTag);
                index = openTagEnd + 1;
                continue;
            }

            // Closing tag: </tag>
            if (tagContent.startsWith("/")) {
                String closingTag = tagContent.substring(1).split("\\s+")[0];
                tagHistory.enqueue(closingTag);

                if (tagStack.isEmpty()) {
                    // Closing tag without matching opening tag
                    extrasQ.enqueue(closingTag);
                } else {
                    String lastOpened = tagStack.peek();
                    if (lastOpened.equals(closingTag)) {
                        // Proper match
                        tagStack.pop();
                    } else if (errorQ.isEmpty() || !errorQ.peek().equals(closingTag)) {
                        // Check if closing tag matches head of errorQ (ignore if matches)
                        if (stackContains(closingTag)) {
                            // Pop stack into errorQ until match
                            while (!tagStack.isEmpty() && !tagStack.peek().equals(closingTag)) {
                                errorQ.enqueue(tagStack.pop());
                            }
                            if (!tagStack.isEmpty()) tagStack.pop(); // Pop the matching tag
                        } else {
                            // Closing tag does not match any opening tag
                            extrasQ.enqueue(closingTag);
                        }
                    } else {
                        // Matches head of errorQ, dequeue and ignore
                        errorQ.dequeue();
                    }
                }
                index = openTagEnd + 1;
                continue;
            }

            // Opening tag: <tag>
            String tagName = tagContent.split("\\s+")[0];
            tagStack.push(tagName);
            tagHistory.enqueue(tagName);
            index = openTagEnd + 1;
        }
    }

    /**
     * Checks if the stack contains the given tag.
     *
     * @param tag the tag to check
     * @return true if the tag is in the stack, false otherwise
     */
    private boolean stackContains(String tag) {
        Iterator<String> iter = tagStack.iterator();
        while (iter.hasNext()) {
            if (iter.next().equals(tag)) return true;
        }
        return false;
    }

    /**
     * Reconcile unmatched tags in errorQ and extrasQ following Kitty's algorithm.
     */
    private void reconcileErrors() {
        while (!errorQ.isEmpty() || !extrasQ.isEmpty()) {
            if (errorQ.isEmpty() && !extrasQ.isEmpty()) {
                while (!extrasQ.isEmpty()) {
                    String extraTag = extrasQ.dequeue();
                    errors.add("Extra closing tag </" + extraTag + "> found without matching opening tag.");
                }
                break;
            } else if (!errorQ.isEmpty() && extrasQ.isEmpty()) {
                while (!errorQ.isEmpty()) {
                    String unclosedTag = errorQ.dequeue();
                    errors.add("Unclosed tag <" + unclosedTag + "> found.");
                }
                break;
            } else {
                String errorTag = errorQ.peek();
                String extraTag = extrasQ.peek();

                if (!errorTag.equals(extraTag)) {
                    errors.add("Tag mismatch error: expected </" + errorTag + "> but found </" + extraTag + ">.");
                    errorQ.dequeue();
                } else {
                    // Both match, remove from both queues without error
                    errorQ.dequeue();
                    extrasQ.dequeue();
                }
            }
        }
    }

    /**
     * Returns list of errors found during parsing.
     *
     * @return list of error messages (empty if valid)
     */
    public List<String> getErrors() {
        return new ArrayList<>(errors);
    }

    /**
     * Prints errors found during parsing and tag history.
     */
    public void printErrors() {
        if (errors.isEmpty()) {
            System.out.println("No errors found.");
        } else {
            System.out.println("Errors:");
            for (String err : errors) {
                System.out.println("  " + err);
            }
        }
        System.out.println("\nTag history (in order seen):");
        Iterator<String> it = tagHistory.iterator();
        while (it.hasNext()) {
            System.out.println("  <" + it.next() + ">");
        }
    }

    /**
     * Returns true if the last parsed file was valid (no errors).
     *
     * @return true if no errors, false otherwise
     */
    public boolean isValid() {
        return errors.isEmpty();
    }
}
