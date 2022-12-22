package com.jamiecheung.apps.columneddataformatter.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class containing common constants.
 *
 * @author JamieCheung
 *
 */
public class CommonConstants {

    /* Space */
    public static final String SPACE = " ";

    /* Empty String */
    public static final String EMPTY_STRING = "";

    /* New line */
    public static final String NEW_LINE = "\n";

    /* Text file extension */
    public static final String TXT_FILE_EXTENSION = ".txt";

    /* The character separating the file name and file extension */
    public static final char FILE_EXTENSION_START_CHARACTER = '.';

    /* The list of characters that separate each element of a file path */
    public static final List<Character> FILE_PATH_SEPARATOR_CHARACTERS = new ArrayList<>(Arrays.asList('/', '\\'));

    /* The String to be appended onto the input file name in the resulting file */
    public static final String RESULT_FILE_SUFFIX = "_formatted";

    /* The index of the first character */
    public static final int FIRST_CHARACTER_INDEX = 0;

    /* Comma followed by a space */
    public static final String COMMA_SPACE = ", ";

    /* The expected number of arguments passed to Executor */
    public static final int EXPECTED_ARGS_LENGTH = 3;

    /* The first expected argument passed to Executor */
    public static final String EXPECTED_ARG_1 = "Input File";

    /* The second expected argument passed to Executor */
    public static final String EXPECTED_ARG_2 = "Result Directory";

    /* The third expected argument passed to Executor */
    public static final String EXPECTED_ARG_3 = "Delimiter";

    /* The arguments passed to Executor has length 0 */
    public static final int ACTUAL_ARGS_LENGTH_ZERO = 0;

    /* The arguments passed to Executor has length 1 */
    public static final int ACTUAL_ARGS_LENGTH_ONE = 1;

    /* The arguments passed to Executor has length 2 */
    public static final int ACTUAL_ARGS_LENGTH_TWO = 2;

    /* The maximum character length of the delimiter argument */
    public static final int DELIMITER_LENGTH_LIMIT = 1;

    /* The time stamp format */
    public static final String TIME_STAMP_FORMAT = "uuuuMMddHHmmss";

    /* The index position of the character separating the date and time */
    public static final int POSITION_OF_DATE_TIME_SEPARATOR = 8;

    /* The character separating the date and time */
    public static final char DATE_TIME_SEPARATOR = 'T';

    /* The line of text to output before the formatted data contents */
    public static final String START_OF_FORMATTED_DATA_CONTENTS = "---START OF FORMATTED DATA CONTENTS---";

    /* The line of text to output after the formatted data contents */
    public static final String END_OF_FORMATTED_DATA_CONTENTS = "---END OF FORMATTED DATA CONTENTS---";

}
