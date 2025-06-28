package appDomain;

import implementations.MyQueue;
import utilities.QueueADT;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * XML Parser that validates the structure of XML files by checking
 * that all opening tags have corresponding closing tags in the correct order.
 * Uses a queue-based approach to track and validate XML tag pairs.
 *
 * @author Your Group Name
 * @version 1.0
 */
public class XMLParser {

    /**
     * Queue to store opening XML tags for validation
     */
    private QueueADT<String> tagQueue;

    /**
     * List to store any errors found during parsing
     */
    private List<String> errors;

    /**
     * Current line number being processed (for error reporting)
     */
    private int currentLine;

    /**
     * Constructs a new XMLParser with an empty queue and error list.
     */
    public XMLParser() {
        this.tagQueue = new MyQueue<>();
        this.errors = new ArrayList<>();
        this.currentLine = 0;
    }

    /**
     * Parses and validates an XML file.
     *
     * @param filename the path to the XML file to parse
     * @return true if the XML is valid, false otherwise
     * @throws IOException if there's an error reading the file
     */
    public boolean parseFile(String filename) throws IOException {
        errors.clear();
        tagQueue.clear();
        currentLine = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = reader.readLine()) != null) {
                currentLine++;
                processLine(line);
            }

            // Check if there are any unclosed tags remaining
            if (!tagQueue.isEmpty()) {
                while (!tagQueue.isEmpty()) {
                    String unclosedTag = tagQueue.dequeue();
                    errors.add("Error: Unclosed tag <" + unclosedTag + ">");
                }
            }

        } catch (IOException e) {
            errors.add("Error reading file: " + e.getMessage());
            throw e;
        }

        return errors.isEmpty();
    }

    /**
     * Processes a single line of XML, extracting and validating tags.
     *
     * @param line the line to process
     */
    private void processLine(String line) {
        if (line == null || line.trim().isEmpty()) {
            return;
        }

        // Find all tags in the line
        int index = 0;
        while (index < line.length()) {
            int tagStart = line.indexOf('<', index);
            if (tagStart == -1) {
                break; // No more tags in this line
            }

            int tagEnd = line.indexOf('>', tagStart);
            if (tagEnd == -1) {
                errors.add("Line " + currentLine + ": Malformed tag - missing closing '>'");
                break;
            }

            String fullTag = line.substring(tagStart + 1, tagEnd);
            processTag(fullTag);

            index = tagEnd + 1;
        }
    }

    /**
     * Processes a single XML tag (without the < > brackets).
     *
     * @param tagContent the content of the tag
     */
    private void processTag(String tagContent) {
        if (tagContent == null || tagContent.trim().isEmpty()) {
            errors.add("Line " + currentLine + ": Empty tag found");
            return;
        }

        tagContent = tagContent.trim();

        // Skip XML declarations, comments, and processing instructions
        if (tagContent.startsWith("?") || tagContent.startsWith("!")) {
            return;
        }

        // Check for self-closing tag
        if (tagContent.endsWith("/")) {
            // Self-closing tag - no need to add to queue
            return;
        }

        // Check if it's a closing tag
        if (tagContent.startsWith("/")) {
            String closingTagName = extractTagName(tagContent.substring(1));
            handleClosingTag(closingTagName);
        } else {
            // Opening tag
            String openingTagName = extractTagName(tagContent);
            if (openingTagName != null && !openingTagName.isEmpty()) {
                tagQueue.enqueue(openingTagName);
            }
        }
    }

    /**
     * Handles a closing tag by checking if it matches the most recent opening tag.
     *
     * @param closingTagName the name of the closing tag
     */
    private void handleClosingTag(String closingTagName) {
        if (tagQueue.isEmpty()) {
            errors.add("Line " + currentLine + ": Closing tag </" + closingTagName +
                    "> found without matching opening tag");
            return;
        }

        String expectedTag = tagQueue.dequeue();
        if (!expectedTag.equals(closingTagName)) {
            errors.add("Line " + currentLine + ": Closing tag </" + closingTagName +
                    "> does not match opening tag <" + expectedTag + ">");
        }
    }

    /**
     * Extracts the tag name from tag content, removing any attributes.
     *
     * @param tagContent the full tag content
     * @return the tag name only
     */
    private String extractTagName(String tagContent) {
        if (tagContent == null || tagContent.trim().isEmpty()) {
            return null;
        }

        // Find the first space or end of string to get just the tag name
        int spaceIndex = tagContent.indexOf(' ');
        if (spaceIndex == -1) {
            return tagContent.trim();
        } else {
            return tagContent.substring(0, spaceIndex).trim();
        }
    }

    /**
     * Returns a list of all errors found during parsing.
     *
     * @return list of error messages
     */
    public List<String> getErrors() {
        return new ArrayList<>(errors);
    }

    /**
     * Returns whether the last parsed XML was valid.
     *
     * @return true if no errors were found, false otherwise
     */
    public boolean isValid() {
        return errors.isEmpty();
    }

    /**
     * Returns the current state of the tag queue (for debugging).
     *
     * @return array of tags currently in the queue
     */
    public Object[] getCurrentTags() {
        return tagQueue.toArray();
    }

    /**
     * Prints all errors to the console.
     */
    public void printErrors() {
        if (errors.isEmpty()) {
            System.out.println("No errors found - XML is valid!");
        } else {
            System.out.println("Errors found:");
            for (String error : errors) {
                System.out.println("  " + error);
            }
        }
    }

    /**
     * Returns a formatted string of all errors.
     *
     * @return formatted error report
     */
    public String getErrorReport() {
        if (errors.isEmpty()) {
            return "XML is valid - no errors found.";
        }

        StringBuilder report = new StringBuilder();
        report.append("XML Validation Errors:\n");
        for (int i = 0; i < errors.size(); i++) {
            report.append((i + 1)).append(". ").append(errors.get(i)).append("\n");
        }
        return report.toString();
    }
}