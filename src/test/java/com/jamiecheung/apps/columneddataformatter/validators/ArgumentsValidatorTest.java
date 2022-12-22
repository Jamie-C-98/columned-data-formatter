package com.jamiecheung.apps.columneddataformatter.validators;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.jamiecheung.apps.columneddataformatter.exceptions.ProgramArgumentException;

/**
 * Class to unit test the {@link ArgumentsValidator} class.
 *
 * @author JamieCheung
 *
 */
class ArgumentsValidatorTest {

    private static final String NEW_LINE = "\n";

    private static final String EXPECTED_ERROR_MESSAGE_NO_ARGUMENTS = "Not enough arguments provided." + NEW_LINE
            + "Provided arguments: " + NEW_LINE + "Missing arguments: Input File, Result Directory, Delimiter";
    private static final String EXPECTED_ERROR_MESSAGE_ONE_ARGUMENT = "Not enough arguments provided." + NEW_LINE
            + "Provided arguments: Input File" + NEW_LINE + "Missing arguments: Result Directory, Delimiter";
    private static final String EXPECTED_ERROR_MESSAGE_TWO_ARGUMENTS = "Not enough arguments provided." + NEW_LINE
            + "Provided arguments: Input File, Result Directory" + NEW_LINE + "Missing arguments: Delimiter";

    private static final String TEST_RESOURCE_DIRECTORY = "src/test/resources/validators_resources/";
    private static final String TEST_INPUT_FILE_NAME = "testInputFile.txt";
    private static final String TEST_VALID_INPUT_FILE = TEST_RESOURCE_DIRECTORY + TEST_INPUT_FILE_NAME;
    private static final String TEST_PATH_DOES_NOT_EXIST = "abc";
    private static final String[] TEST_ARGS_NO_ARGUMENTS = new String[] {};
    private static final String[] TEST_ARGS_ONE_ARGUMENT = new String[] { "a" };
    private static final String[] TEST_ARGS_TWO_ARGUMENTS = new String[] { "b", "c" };
    private static final String[] TEST_ARGS_THREE_ARGUMENTS = new String[] { "d", "e", "f" };
    private static final String[] TEST_ARGS_FOUR_ARGUMENTS = new String[] { "g", "h", "i", "j" };

    /**
     * Unit tests the {@link ArgumentsValidator#validateArguments(String[] args)}
     * function.
     */
    @Test
    public void testArgumentsValidation() {

        // Run the validateArguments function and ensure the correct exception is thrown
        // for an args of length 0
        ProgramArgumentException exceptionNoArguments = assertThrows(ProgramArgumentException.class,
                () -> ArgumentsValidator.validateArguments(TEST_ARGS_NO_ARGUMENTS));
        assertEquals(EXPECTED_ERROR_MESSAGE_NO_ARGUMENTS, exceptionNoArguments.getMessage());

        // Run the validateArguments function and ensure the correct exception is thrown
        // for an args of length 1
        ProgramArgumentException exceptionOneArgument = assertThrows(ProgramArgumentException.class,
                () -> ArgumentsValidator.validateArguments(TEST_ARGS_ONE_ARGUMENT));
        assertEquals(EXPECTED_ERROR_MESSAGE_ONE_ARGUMENT, exceptionOneArgument.getMessage());

        // Run the validateArguments function and ensure the correct exception is thrown
        // for an args of length 2
        ProgramArgumentException exceptionTwoArguments = assertThrows(ProgramArgumentException.class,
                () -> ArgumentsValidator.validateArguments(TEST_ARGS_TWO_ARGUMENTS));
        assertEquals(EXPECTED_ERROR_MESSAGE_TWO_ARGUMENTS, exceptionTwoArguments.getMessage());

        // Run the validateArguments function and ensure no exception is thrown for an
        // args of length 3
        assertDoesNotThrow(() -> ArgumentsValidator.validateArguments(TEST_ARGS_THREE_ARGUMENTS));

        // Run the validateArguments function and ensure no exception is thrown for an
        // args of length greater than 3
        assertDoesNotThrow(() -> ArgumentsValidator.validateArguments(TEST_ARGS_FOUR_ARGUMENTS));

    }

    /**
     * Unit tests the {@link ArgumentsValidator#isArgumentAPath(String arg)}
     * function.
     */
    @Test
    public void testIsArgumentAPath() {

        // Run the isArgumentAPath function on a valid input file and check it returns
        // true
        assertTrue(ArgumentsValidator.isArgumentAPath(TEST_VALID_INPUT_FILE));

        // Run the isArgumentAPath function on an invalid path and check it returns
        // false
        assertFalse(ArgumentsValidator.isArgumentAPath(TEST_PATH_DOES_NOT_EXIST));

    }

}