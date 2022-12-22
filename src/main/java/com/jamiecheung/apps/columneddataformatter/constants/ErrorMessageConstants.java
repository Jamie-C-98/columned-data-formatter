package com.jamiecheung.apps.columneddataformatter.constants;

import static com.jamiecheung.apps.columneddataformatter.constants.CommonConstants.NEW_LINE;

/**
 * Class containing constant error messages.
 *
 * @author JamieCheung
 *
 */
public class ErrorMessageConstants {

    /* Error message for when not enough arguments are passed into Executor */
    public static final String ERROR_MESSAGE_NOT_ENOUGH_ARGS = "Not enough arguments provided." + NEW_LINE
            + "Provided arguments: %s" + NEW_LINE + "Missing arguments: %s";

    /* Error message for when the input file argument does not exist in the file system */
    public static final String ERROR_MESSAGE_INPUT_FILE_DOES_NOT_EXIST = "Input file %s does not exist.";

    /* Error message for when the input file argument is not a file */
    public static final String ERROR_MESSAGE_INPUT_FILE_NOT_A_FILE = "Input file %s is not a file.";

    /* Error message for when the result directory argument is not provided */
    public static final String ERROR_MESSAGE_RESULT_DIRECTORY_NOT_PROVIDED = "Result directory not provided.";

    /* Error message for when the result directory argument does not exist in the file system */
    public static final String ERROR_MESSAGE_RESULT_DIRECTORY_DOES_NOT_EXIST = "Result directory %s does not exist.";

    /* Error message for when the result directory argument is not a folder */
    public static final String ERROR_MESSAGE_RESULT_DIRECTORY_NOT_A_DIRECTORY = "Result directory %s is not a directory.";

    /* Error message for when the delimiter argument is more than 1 character in length */
    public static final String ERROR_MESSAGE_DELIMITER_NOT_A_SINGLE_CHARACTER = "Delimiter %s is not a single character.";

}
