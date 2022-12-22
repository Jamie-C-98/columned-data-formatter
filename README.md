# Columned Data Formatter

## Table of contents

- Introduction
- Requirements
- Build Executable jar
- Application Configuration
- Run Application
- Maintainers

## Introduction

``columned-data-formatter`` is a Java application for converting some text that represents columns of data into a human-readable form.

For example, running the application on some String or file containing the following text:

```
| First Name |   Surname |Email Address| Mobile Number   |    
    | Some one | 2ndName | a@b.com 
  Someone | SecondName
    |Some1 |SomeName||   999|   
```

will transform it into the following:

```
| First Name | Surname    | Email Address | Mobile Number |
| Some one   | 2ndName    | a@b.com       |               |
| Someone    | SecondName |               |               |
| Some1      | SomeName   |               | 999           |
```

In this example, we say that ``|`` is the delimiter character that separates the data entries and forms the columns.

## Requirements

- Maven (if attempting to build the executable jar file)

## Build Executable jar

Build the whole solution by running the following command from the repository root:

```
mvn clean install
```

This will generate the executable jar file within the following directory: ``./executable_application/executable_jar/``

NOTE:
- ``columned-data-formatter-1.0.0-SNAPSHOT.jar`` has already been placed into this directory, so the application can be ran without this step. This step is only required if modifications are made to the source files in the directory: ``./src/``
- If there are multiple ``columned-data-formatter-<version number>.jar`` files in this directory, the application will always execute the jar file with the latest version number.

## Application Configuration

The input parameters, as well as other parameters for the application can be configured in ``./executable_application/config/config.properties``

Before running the application, these should be updated accordingly.

The list of properties in ``./executable_application/config/config.properties`` can be found in the table below:

  <table border="1">
    <thead>
      <tr>
        <th>Property</th>
        <th>Required</th>
        <th>Type</th>
        <th>Default</th>
        <th>Description</th>
      </tr>
    </thead>
    <tbody>
        <tr>
            <td><code>delimiter</code></td>
            <td>Yes.</td>
            <td>Character</td>
            <th></th>
            <td>The character that divides the data on each row, forming the columns.</td>
        </tr>
        <tr>
            <td><code>format.data.contents</code></td>
            <td>No.</td>
            <td>Boolean</td>
            <td>false</td>
            <td>Flag to control whether the contents in between <code>---START OF DATA CONTENTS---</code> and <code>---END OF DATA CONTENTS---</code> should be formatted.</td>
        </tr>
        <tr>
            <td>
            <code>---START OF DATA CONTENTS---</code>
            <br>
            <code>...</code>
            <br>
            <code>---END OF DATA CONTENTS---</code>
            </td>
            <td>No.</td>
            <td>Multi-line String</td>
            <td></td>
            <td>The contents to be formatted.</td>
        </tr>
        <tr>
            <td><code>format.input.file.data.contents</code></td>
            <td>No.</td>
            <td>Boolean</td>
            <td>false</td>
            <td>
            Flag to control whether the <code>input.file</code> or the file(s) in <code>input.directory</code> should be formatted.
            <br>
            NOTE: If this is set to true, at least one of <code>input.file</code> or <code>input.directory</code> is required.
            </td>
        </tr>
        <tr>
            <td><code>input.file</code></td>
            <td>No, unless <code>format.input.file.data.contents</code> is true and <code>input.directory</code> is not provided.</td>
            <td>String</td>
            <td></td>
            <td>The file containing the data to be formatted.</td>
        </tr>
        <tr>
            <td><code>input.directory</code></td>
            <td>No, unless <code>format.input.file.data.contents</code> is true and <code>input.file</code> is not provided.</td>
            <td>String</td>
            <td></td>
            <td>The location in the file system containing the file(s) to be formatted.</td>
        </tr>
        <tr>
            <td><code>result.directory</code></td>
            <td>No.</td>
            <td>String</td>
            <td></td>
            <td>The location in the file system to write out the file(s) containing the formatted data.</td>
        </tr>
        <tr>
            <td><code>clear.result.directory.before.execution</code></td>
            <td>No.</td>
            <td>Boolean</td>
            <td>false</td>
            <td>Flag to control whether the contents of <code>result.directory</code> should be deleted before the program is ran.</td>
        </tr>
    </tbody>
  </table>

## Run Application

1. Open terminal
2. Execute the shell script ``./executable_application/run.sh``

The output is the transformed text printed into the terminal console and resulting file(s) in ``result.directory`` (if provided) containing the formatted text.

If the application is run on an input file ``example_input_file.txt``, the resulting file will be of the same type as the input file (txt in this case) and the resulting file name will contain the input file name followed by the suffix ``_formatted`` (``example_input_file_formatted.txt`` in this case).

If the application is run on an input String, the resulting file will be of type txt and the resulting file name will contain the time-stamp of when the application began followed by the suffix ``_formatted``, for example, if the application runs on 1st February 6543 at time 07:08:09, it will be ``65430201T070809_formatted.txt``.

If a file with the same name as the resulting file already exists within ``result.directory``, its contents will be overwritten.

## Maintainers

Current maintainers:
- Jamie Cheung