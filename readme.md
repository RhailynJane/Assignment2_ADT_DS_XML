===========================================
XML Parser & Validator - readMe.txt
===========================================

Assignment: CPRG304B - Assignment 2  
Group Members:
- Komalpreet Kaur
- Anne Marie Ala
- Rhailyn Jane Cona
- Abel Fekadu

Overview:
---------
This is a Java-based XML Parser that validates XML document structure using a custom-built queue and list-based data structure. It ensures proper nesting of tags, handles self-closing tags, ignores comments and XML declarations, and reports any structural errors with line numbers.

Submission Includes:
--------------------
- `Parser.jar` (Executable JAR)
- `readMe.txt` (This file)
- `/doc` folder (Javadoc with -private scope)
- Complete IntelliJ project folder
- Marking criteria document (filled out and signed)

===========================================
How to Run the Program (JAR-based)
===========================================

Requirements:
-------------
- Java JDK 8 or higher installed
- Terminal or Command Prompt access

Steps:
------
1. Open a terminal and navigate to the folder containing `Parser.jar`
2. Run the parser with one of the following:

   a) Provide an XML file as argument:
   java -jar Parser.jar yourfile.xml

   b) No argument (interactive mode):
   java -jar Parser.jar

   Example:
   java -jar Parser.jar test-files/valid.xml

===========================================
IntelliJ IDEA - Building the Project
===========================================

If building from IntelliJ:

1. Open IntelliJ and your project (A2Group4).
2. Go to File > Project Structure > Artifacts.
3. Add a new JAR:
   - Type: JAR > From Modules with Dependencies
   - Main class: appDomain.Parser
   - Choose: "Extract to the target JAR"
4. Go to Build > Build Artifacts > Parser:jar > Build
5. The JAR will appear under:
   out/artifacts/Parser/Parser.jar

===========================================
Javadoc Generation Instructions
===========================================

To generate documentation:

1. Tools > Generate JavaDoc...
2. Choose all source packages (e.g., appDomain, implementations, utilities)
3. Set visibility to: private
4. Output to: `doc` folder
5. Click OK

Open `doc/index.html` in any browser to view the documentation.

===========================================
Sample XML Files for Testing
===========================================

**valid.xml**
<?xml version="1.0" encoding="UTF-8"?>
<root>
    <person id="1">
        <name>John Doe</name>
        <age>30</age>
    </person>
    <selfClosing />
</root>

**invalid.xml**
<?xml version="1.0" encoding="UTF-8"?>
<root>
    <person>
        <name>John Doe</name>
        <age>30
</root>

Place these in a `test-files/` folder for testing.

===========================================
Sample Program Output
===========================================

✓ SUCCESS: XML file is valid!
All opening tags have matching closing tags.

✗ VALIDATION FAILED: XML file has errors.
1. Line 4: Malformed tag - missing closing '>'
2. Error: Unclosed tag <person>

===========================================
Common Issues / Troubleshooting
===========================================

- "Class not found": Ensure you're using `java -jar Parser.jar` or correct classpath
- "File not found": Make sure the XML file exists and path is correct
- Compilation errors: Verify all files exist and Java version is 8+

===========================================
Core Components & Data Structures
===========================================

- appDomain.Parser: CLI and main entry point
- appDomain.XMLParser: Core parsing logic
- implementations.MyQueue: Custom circular queue
- implementations.MyArrayList / MyDLL: List structures
- utilities.*: Interfaces (QueueADT, ListADT)
- exceptions.*: Custom exceptions

Algorithm:
----------
- Enqueue opening tags
- On closing tag, dequeue and compare
- Validate nesting and structure
- Report errors with line number

===========================================
Notes
===========================================

- This is a console-based Java program. It does not include a GUI.
- It handles multiple files via batch processing (if enabled in code).
- Built and tested using IntelliJ IDEA 2023.

===========================================
End of Document
===========================================
