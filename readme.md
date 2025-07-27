
# XML Parser & Validator

**Assignment:** CPRG304B - Assignment 2  
**Group Members:**
- Komalpreet Kaur
- Anne Marie Ala
- Rhailyn Jane Cona
- Abel Fekadu
- Samuel Braun

---

## 📘 Overview

This is a Java-based XML Parser that validates the structure of XML documents using a custom-built queue and list-based data structure. It ensures proper nesting of tags, handles self-closing tags, ignores comments and XML declarations, and reports structural errors with line numbers.

---

## 📁 Submission Includes

- `Parser.jar` (Executable JAR)
- `readMe.txt` (This file)
- `/doc` folder (Javadoc with `-private` scope)
- Complete IntelliJ project folder
- Marking criteria document (filled out and signed)

---

## Running the Application

### Prerequisites

- Java 21 or later must be installed.
- If your system default Java version is older than the version used to compile the program, you need to run the JAR with the correct Java runtime explicitly.

### How to Run

1. Open PowerShell.
2. Navigate to the project directory containing `Parser.jar`.
3. Run the following command to execute the parser on a sample XML file (`test-files/valid.xml`):

```powershell
& "C:\Program Files\Java\jdk-23\bin\java.exe" -jar Parser.jar test-files/valid.xml
```

Replace the Java path with your installed JDK 23 path if different.

---

## 🧰 IntelliJ IDEA - Building the Project

If you're building the project using IntelliJ IDEA:

1. Open IntelliJ and the project (`A2Group4`).
2. Go to **File > Project Structure > Artifacts**.
3. Add a new JAR:
   - Type: **JAR > From modules with dependencies**
   - Main class: `appDomain.Parser`
   - Output layout: Select "Extract to the target JAR"
4. Go to **Build > Build Artifacts > Parser:jar > Build**
5. The generated JAR file will be located at:

```
out/artifacts/Parser/Parser.jar
```

---

## 📄 Javadoc Generation Instructions

To generate documentation:

1. Go to **Tools > Generate JavaDoc...**
2. Select all source packages (`appDomain`, `implementations`, `utilities`)
3. Set visibility to: `private`
4. Output directory: `doc` folder
5. Click **OK**

Open `doc/index.html` in any browser to view the generated documentation.

---

## 📂 Sample XML Files for Testing

### ✅ valid.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<root>
   <child>Content</child>
</root>
```

### ❌ invalid.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<library>
   <book>
   <title>Refactoring</title>
   <author>Martin Fowler</author>
   <!-- Missing closing tag for book -->
   <book
      <title>The Pragmatic Programmer</title>
      <author>Andrew Hunt</author>
   </book>
   
</library>
```

> 📌 Place these files in a folder named `test-files/` for testing.

---

## 🖨️ Sample Program Output

### ✅ Valid XML Output

```
✓ SUCCESS: XML file is valid!
All opening tags have matching closing tags.
```

### ❌ Invalid XML Output

```
✗ VALIDATION FAILED: XML file has errors.
1. Line 4: Malformed tag - missing closing '>'
2. Error: Unclosed tag <person>
```

---

## 🛠️ Common Issues / Troubleshooting

- **"Class not found"**: Ensure you're using `java -jar Parser.jar` or the correct classpath.
- **"File not found"**: Confirm the XML file exists and the path is correct.
- **Compilation errors**: Verify that all files are present, and you're using Java 8 or higher.

---

## 🧩 Core Components & Data Structures

- `appDomain.Parser`: Command-line interface and main entry point
- `appDomain.XMLParser`: Core parsing logic
- `implementations.MyQueue`: Custom circular queue
- `implementations.MyArrayList`, `MyDLL`: List implementations
- `utilities.*`: Interfaces (`QueueADT`, `ListADT`)
- `exceptions.*`: Custom exception classes

---

## 🧠 Algorithm Summary

- Enqueue opening tags
- On encountering a closing tag, dequeue and compare
- Validate tag nesting and structure
- Report structural errors with line numbers

---

## 🔎 Notes

- This is a **console-based** Java program; no GUI is included.
- Supports **batch processing** for multiple XML files (if enabled in the code).
- Built and tested using **IntelliJ IDEA 2023**.

---

**📌 End of Document**
