package appDomain;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Main class for the XML Parser application.
 * Provides command-line interface for parsing and validating XML files.
 *
 * Usage: java appDomain.Parser [filename]
 * If no filename is provided, the program will prompt for one.
 *
 * @author Rhailyn Cona, Komalpreet Kaur, Anne Marie Ala, Abel Fekadu, Samuel Braun
 * @version 1.1
 */
public class Parser {

    /**
     * Main method - entry point for the XML Parser application.
     *
     * @param args command line arguments - expects XML filename or -h/--help
     */
    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println("    XML Parser & Validator");
        System.out.println("=================================");

        Parser parser = new Parser();

        if (args.length > 0) {
            String firstArg = args[0].trim();
            if (firstArg.equalsIgnoreCase("-h") || firstArg.equalsIgnoreCase("--help")) {
                displayUsage();
                return;
            }
            parser.parseFile(firstArg);
        } else {
            parser.runInteractiveMode();
        }
    }

    /**
     * Runs the parser in interactive mode, prompting user for filenames.
     */
    private void runInteractiveMode() {
        // Use try-with-resources to ensure Scanner is closed properly
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\nEnter XML filename to parse (or 'quit' to exit):");
                System.out.print("> ");

                String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("quit") || input.equalsIgnoreCase("q")) {
                    System.out.println("Thank you for using XML Parser!");
                    break;
                }

                if (input.isBlank()) {
                    System.out.println("Please enter a filename.");
                    continue;
                }

                parseFile(input);
            }
        }
    }

    /**
     * Parses and validates the specified XML file.
     *
     * @param filename the path to the XML file to parse
     */
    private void parseFile(String filename) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Parsing file: " + filename);
        System.out.println("=".repeat(50));

        // Validate file existence and accessibility
        File file = new File(filename);
        if (!file.exists()) {
            System.err.println("Error: File '" + filename + "' not found.");
            return;
        }

        if (!file.isFile()) {
            System.err.println("Error: '" + filename + "' is not a valid file.");
            return;
        }

        if (!file.canRead()) {
            System.err.println("Error: Cannot read file '" + filename + "'.");
            return;
        }

        XMLParser xmlParser = new XMLParser();

        try {
            long startTime = System.currentTimeMillis();
            boolean isValid = xmlParser.parseFile(filename);
            long endTime = System.currentTimeMillis();

            System.out.println("\nParsing completed in " + (endTime - startTime) + " ms");
            System.out.println("File size: " + file.length() + " bytes");

            if (isValid) {
                System.out.println("\n✓ SUCCESS: XML file is valid!");
                System.out.println("  All opening tags have matching closing tags.");
            } else {
                System.out.println("\n✗ VALIDATION FAILED: XML file has errors.\n");
                xmlParser.printErrors();
            }

            int errorCount = xmlParser.getErrors().size();
            System.out.println("\nSummary:");
            System.out.println("  Errors found: " + errorCount);
            System.out.println("  Status: " + (isValid ? "VALID" : "INVALID"));

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error during parsing: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Displays usage information for the application.
     */
    private static void displayUsage() {
        System.out.println("\nUsage:");
        System.out.println("  java appDomain.Parser [filename]");
        System.out.println();
        System.out.println("Arguments:");
        System.out.println("  filename    Path to the XML file to parse (optional)");
        System.out.println("  -h, --help  Show this help message");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("  java appDomain.Parser sample.xml");
        System.out.println("  java appDomain.Parser data/document.xml");
        System.out.println("  java appDomain.Parser              (interactive mode)");
        System.out.println();
        System.out.println("The parser validates that:");
        System.out.println("  • All opening tags have matching closing tags");
        System.out.println("  • Tags are properly nested");
        System.out.println("  • Self-closing tags are handled correctly");
        System.out.println("  • XML declarations and comments are ignored");
    }

    /**
     * Validates multiple XML files and provides a summary report.
     * This method can be used for batch processing.
     *
     * @param filenames array of XML filenames to validate
     */
    public void validateMultipleFiles(String[] filenames) {
        System.out.println("Batch validation of " + filenames.length + " files:");
        System.out.println("=".repeat(60));

        int validFiles = 0;
        int invalidFiles = 0;

        for (String filename : filenames) {
            System.out.println("\nValidating: " + filename);
            XMLParser xmlParser = new XMLParser();

            try {
                boolean isValid = xmlParser.parseFile(filename);
                if (isValid) {
                    System.out.println("  ✓ VALID");
                    validFiles++;
                } else {
                    System.out.println("  ✗ INVALID (" + xmlParser.getErrors().size() + " errors)");
                    invalidFiles++;
                }
            } catch (IOException e) {
                System.out.println("  ✗ ERROR: " + e.getMessage());
                invalidFiles++;
            }
        }

        // Summary report
        System.out.println("\n" + "=".repeat(60));
        System.out.println("BATCH VALIDATION SUMMARY");
        System.out.println("=".repeat(60));
        System.out.println("Total files processed: " + filenames.length);
        System.out.println("Valid files: " + validFiles);
        System.out.println("Invalid files: " + invalidFiles);
        System.out.println("Success rate: " + String.format("%.1f%%",
                (validFiles * 100.0) / filenames.length));
    }
}
