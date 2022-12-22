# Script to run the columned-data-formatter application.

EMPTY_LINE=""
NEW_LINE="\n"

# Log for the start of the application.
echo "$EMPTY_LINE"
echo Starting application...
echo "$EMPTY_LINE"

# Properties file containing values to configure the application.
PROPERTY_FILE=./configuration/config.properties

# Check the properties file exists.
if [ ! -f "$PROPERTY_FILE" ]
then
	echo ERROR: $PROPERTY_FILE not found.
	echo "$EMPTY_LINE"
	echo Terminating application...
	echo "$EMPTY_LINE"
	exit
fi

# Function to get the mapped value for a specified key in the properties file.
function getProperty {
	PROP_KEY=$1
	# Excludes "#" to ignore comment lines.
	# Excludes ".$PROP_KEY."/".$PROP_KEY"/"$PROP_KEY." to ignore properties whose name contains the property name we are searching for.
	PROP_VALUE=`cat $PROPERTY_FILE | grep -v "#" | grep -v -F ".$PROP_KEY." | grep -v -F ".$PROP_KEY" | grep -v -F "$PROP_KEY." | grep -F "$PROP_KEY" | head -1 | cut -d'=' -f2`
	echo $PROP_VALUE
}

# Function to get the multi-line property from the properties file.
function getMultiLineProperty {
	PROP_KEY=$1
	PROP_VALUE=`cat "$PROPERTY_FILE" | grep -v "#" | awk '/---START OF DATA CONTENTS---/{f=1;next} /---END OF DATA CONTENTS---/{f=0} f {print nd $0}'`
	echo "$PROP_VALUE"
}

# Load the properties from the properties file.
DELIMITER_KEY="delimiter"
FORMAT_DATA_CONTENTS_KEY="format.data.contents"
FORMAT_INPUT_FILE_DATA_CONTENTS_KEY="format.input.file.data.contents"
INPUT_FILE_KEY="input.file"
INPUT_DIRECTORY_KEY="input.directory"
RESULT_DIRECTORY_KEY="result.directory"
RESULT_DIRECTORY_CLEAN_UP_BEFORE_EXECUTION_KEY="clear.result.directory.before.execution"

# Set the arguments for the application.
DELIMITER=$(getProperty $DELIMITER_KEY)
FORMAT_DATA_CONTENTS=$(getProperty $FORMAT_DATA_CONTENTS_KEY)
FORMAT_INPUT_FILE_DATA_CONTENTS=$(getProperty $FORMAT_INPUT_FILE_DATA_CONTENTS_KEY)
DATA_CONTENTS=$(getMultiLineProperty)
INPUT_FILE=$(getProperty $INPUT_FILE_KEY)
INPUT_DIRECTORY=$(getProperty $INPUT_DIRECTORY_KEY)
RESULT_DIRECTORY=$(getProperty $RESULT_DIRECTORY_KEY)
RESULT_DIRECTORY_CLEAN_UP_BEFORE_EXECUTION=$(getProperty $RESULT_DIRECTORY_CLEAN_UP_BEFORE_EXECUTION_KEY)

# Check required properties are provided before running the application for an input String.
if [ "$FORMAT_DATA_CONTENTS" == true ]
then
	if [ -z "$DELIMITER" ]
		then
		echo ERROR: $DELIMITER_KEY is required.
		echo "$EMPTY_LINE"
		echo Terminating application...
		echo "$EMPTY_LINE"
		exit
	fi
fi

# Check required properties are provided before running the application for input file(s).
if [ "$FORMAT_INPUT_FILE_DATA_CONTENTS" == true ]
then
	if [ -z "$DELIMITER" ] || [ -z "$INPUT_FILE" -a -z "$INPUT_DIRECTORY" ]
	then

		if [ -z "$DELIMITER" ]
			then
			echo ERROR: $DELIMITER_KEY is required.
			echo "$EMPTY_LINE"
		fi

		if [ -z "$INPUT_FILE" -a -z "$INPUT_DIRECTORY" ]
			then
			echo ERROR: One of $INPUT_FILE_KEY or $INPUT_DIRECTORY_KEY is required.
			echo "$EMPTY_LINE"
		fi

		echo Terminating application...
		echo "$EMPTY_LINE"
		exit
	fi
fi

# Get all files in the result directory.
RESULT_DIRECTORY_FILES="$RESULT_DIRECTORY*"

# Clear the result directory before running the program if configured to do so.
if [ ! -z "$RESULT_DIRECTORY" ] && [ "$RESULT_DIRECTORY_CLEAN_UP_BEFORE_EXECUTION" == true ]
then
	echo Clearing the result directory "$RESULT_DIRECTORY" before execution.
	rm -r $RESULT_DIRECTORY_FILES
	echo "$EMPTY_LINE"
fi

# Sort the jar files, with the latest version first.
SORT_EXECUTABLE_JAR=$(ls ./executable_jar/columned-data-formatter-*.jar | sort -t- -k2 -V -r)

# Set the location of the main class entry point for the columned-data-formatter.
MAIN_CLASS_LOCATION=com.jamiecheung.apps.columneddataformatter.executors.Executor

# Get the jar with the latest version.
set -- $SORT_EXECUTABLE_JAR
LATEST_VERSION_EXECUTABLE_JAR=$1

# Running the application for an input String.
if [ "$FORMAT_DATA_CONTENTS" == true ]
then
	echo Running $LATEST_VERSION_EXECUTABLE_JAR on contents: "$NEW_LINE" "$DATA_CONTENTS"
	echo "$EMPTY_LINE"
	java -cp $LATEST_VERSION_EXECUTABLE_JAR $MAIN_CLASS_LOCATION "$DELIMITER" "$DATA_CONTENTS" "$RESULT_DIRECTORY"
	echo "$EMPTY_LINE"
fi

# Running the application for an input file(s).
if [ "$FORMAT_INPUT_FILE_DATA_CONTENTS" == true ]
then
	if [ ! -z "$INPUT_FILE" ]
	then
		# If input.file is provided, run the application against the input.file.
		echo Running $LATEST_VERSION_EXECUTABLE_JAR on input file $INPUT_FILE
		echo "$EMPTY_LINE"
		java -cp $LATEST_VERSION_EXECUTABLE_JAR $MAIN_CLASS_LOCATION "$DELIMITER" "$INPUT_FILE" "$RESULT_DIRECTORY"
		echo "$EMPTY_LINE"
	fi

	if [ ! -z "$INPUT_DIRECTORY" ]
	then
		# If input.directory is empty, run the application against all files in the input.directory.
		echo Running $LATEST_VERSION_EXECUTABLE_JAR on all files in $INPUT_DIRECTORY
		echo "$EMPTY_LINE"
		find $INPUT_DIRECTORY -type f -name "*.*" | while read fileToFormat; do
	  		echo Running $LATEST_VERSION_EXECUTABLE_JAR on input file $fileToFormat
	  		echo "$EMPTY_LINE"
	  		java -cp $LATEST_VERSION_EXECUTABLE_JAR $MAIN_CLASS_LOCATION "$DELIMITER" "$fileToFormat" "$RESULT_DIRECTORY"
	  		echo "$EMPTY_LINE"
		done
	fi
fi

# Log for the end of the application.
echo Execution completed successfully. Terminating application...
echo "$EMPTY_LINE"