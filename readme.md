# XML Parser Project

A Java-based XML parser that validates XML document structure using a queue-based approach to ensure proper tag nesting and closure.

## Group Members
- **Member 1**: .Kaur, Komalpreet
- **Member 2**: Ala, Anne Marie
- **Member 3**: Cona, Rhailyn Jane
- **Member 4**: Fekadu, Abel

## Features

- **Queue-based validation**: Uses FIFO principle to validate XML tag nesting
- **Comprehensive error reporting**: Shows line numbers and specific error types
- **Multiple input modes**: Command-line arguments or interactive mode
- **Batch processing**: Can validate multiple files at once
- **Performance metrics**: Shows parsing time and file statistics
- **Robust error handling**: Handles malformed XML and file I/O errors

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- Command line access (Terminal/Command Prompt)

## Quick Start

### 1. Download and Setup

Clone or download the project files and organize them according to the project structure above.

### 2. Compile the Project

#### Option A: Manual Compilation
```bash
# Navigate to the project directory
cd XMLParser

# Create output directory
mkdir -p out

# Compile all Java files
javac -d out src/utilities/*.java src/implementations/*.java src/appDomain/*.java
```

#### Option B: Using Compilation Script

**Windows (compile.bat):**
```batch
@echo off
echo Compiling XML Parser...
mkdir out 2>NUL
javac -d out src/utilities/*.java src/implementations/*.java src/appDomain/*.java
if %ERRORLEVEL% EQU 0 (
    echo Compilation successful!
) else (
    echo Compilation failed!
)
```

**Linux/Mac (compile.sh):**
```bash
#!/bin/bash
echo "Compiling XML Parser..."
mkdir -p out
javac -d out src/utilities/*.java src/implementations/*.java src/appDomain/*.java
if [ $? -eq 0 ]; then
    echo "Compilation successful!"
else
    echo "Compilation failed!"
fi
```

Make the script executable and run:
```bash
chmod +x compile.sh
./compile.sh
```

### 3. Create Test Files

Create some sample XML files to test the parser:

**valid.xml** (Valid XML):
```xml
<?xml version="1.0" encoding="UTF-8"?>
<root>
    <person id="1">
        <name>John Doe</name>
        <age>30</age>
        <address>
            <street>123 Main St</street>
            <city>Anytown</city>
        </address>
    </person>
    <selfClosing />
</root>
```

**invalid.xml** (Invalid XML):
```xml
<?xml version="1.0" encoding="UTF-8"?>
<root>
    <person>
        <name>John Doe</name>
        <age>30
        <address>
            <street>123 Main St</street>
        </address>
    </person>
</root>
```

## Running the Parser

### Method 1: Command Line with Filename
```bash
# Navigate to project directory
cd XMLParser

# Run with specific file
java -cp out appDomain.Parser test-files/valid.xml
java -cp out appDomain.Parser test-files/invalid.xml
```

### Method 2: Interactive Mode
```bash
# Run without arguments for interactive mode
java -cp out appDomain.Parser
```

Then enter filenames when prompted:
```
=================================
    XML Parser & Validator
=================================

Enter XML filename to parse (or 'quit' to exit):
> test-files/valid.xml
```

### Method 3: Using Scripts

**Windows (run.bat):**
```batch
@echo off
java -cp out appDomain.Parser %*
```

**Linux/Mac (run.sh):**
```bash
#!/bin/bash
java -cp out appDomain.Parser "$@"
```

Usage:
```bash
./run.sh test-files/valid.xml
./run.sh  # Interactive mode
```

## Sample Output

### Valid XML File:
```
==================================================
Parsing file: test-files/valid.xml
==================================================

Parsing completed in 5 ms
File size: 245 bytes

✓ SUCCESS: XML file is valid!
  All opening tags have matching closing tags.

Summary:
  Errors found: 0
  Status: VALID
```

### Invalid XML File:
```
==================================================
Parsing file: test-files/invalid.xml
==================================================

Parsing completed in 3 ms
File size: 198 bytes

✗ VALIDATION FAILED: XML file has errors.

Errors found:
  1. Line 4: Malformed tag - missing closing '>'
  2. Error: Unclosed tag <root>
  3. Error: Unclosed tag <person>

Summary:
  Errors found: 3
  Status: INVALID
```

## Testing the Implementation

### Test Cases to Try

1. **Valid XML with nested tags**
2. **Missing closing tags**
3. **Mismatched tag names**
4. **Self-closing tags**
5. **XML with attributes**
6. **Empty files**
7. **Files with only comments**
8. **Malformed tags**

### Creating Test Files

Create a `test-files` directory with various XML samples:

```bash
mkdir test-files

# Create test files with different scenarios
cat > test-files/nested.xml << 'EOF'
<root>
    <level1>
        <level2>
            <level3>Content</level3>
        </level2>
    </level1>
</root>
EOF

cat > test-files/mismatched.xml << 'EOF'
<root>
    <person>
        <name>John</name>
    </student>
</root>
EOF
```

## Troubleshooting

### Common Issues

1. **"Class not found" error**
    - Ensure you're running from the correct directory
    - Check that compilation was successful
    - Verify the classpath: `java -cp out appDomain.Parser`

2. **"File not found" error**
    - Check file paths are correct
    - Use absolute paths if necessary
    - Ensure test files exist

3. **Compilation errors**
    - Verify all Java files are in correct directories
    - Check Java version compatibility
    - Ensure all dependencies are present

### Debug Mode

Add debug output by modifying the XMLParser class or run with verbose output:
```bash
java -cp out -verbose appDomain.Parser test-files/sample.xml
```

## Project Components

### Core Classes

1. **QueueADT<E>** - Interface defining queue operations
2. **MyQueue<E>** - Array-based queue implementation with dynamic resizing
3. **EmptyQueueException** - Custom exception for empty queue operations
4. **XMLParser** - Core parsing logic using queue for tag validation
5. **Parser** - Main class with command-line interface

### Algorithm Overview

The parser uses a queue-based approach:

1. **Opening tags** are enqueued as they're encountered
2. **Closing tags** trigger dequeue operations
3. **Tag matching** ensures proper nesting (FIFO principle)
4. **Error reporting** tracks issues with line numbers
5. **Final validation** checks for unclosed tags

## Performance Notes

- **Time Complexity**: O(n) where n is the file size
- **Space Complexity**: O(t) where t is the maximum nesting depth
- **Memory Usage**: Dynamic array resizing for efficient memory usage

## Contributing

When extending this project:

1. Follow existing code style and documentation
2. Add comprehensive error handling
3. Include unit tests for new features
4. Update this README with new functionality

## License

This project is created for educational purposes. Feel free to use and modify as needed.

---

*For questions or issues, please refer to the source code comments or create test cases to verify functionality.*