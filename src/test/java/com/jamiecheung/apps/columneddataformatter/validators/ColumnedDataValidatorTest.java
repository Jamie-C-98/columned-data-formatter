package com.jamiecheung.apps.columneddataformatter.validators;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.jamiecheung.apps.columneddataformatter.exceptions.ColumnedDataPropertyException;

/**
 * Class to unit test the {@link ColumnedDataValidator} class.
 *
 * @author JamieCheung
 *
 */
class ColumnedDataValidatorTest {

    private static final String NEW_LINE = "\n";

    private static final String EXPECTED_ERROR_MESSAGE_INPUT_FILE_DOES_NOT_EXIST = "Input file %s does not exist.";
    private static final String EXPECTED_ERROR_MESSAGE_INPUT_FILE_NOT_A_FILE = "Input file %s is not a file.";
    private static final String EXPECTED_ERROR_MESSAGE_RESULT_DIRECTORY_NOT_PROVIDED = "Result directory not provided.";
    private static final String EXPECTED_ERROR_MESSAGE_RESULT_DIRECTORY_DOES_NOT_EXIST = "Result directory %s does not exist.";
    private static final String EXPECTED_ERROR_MESSAGE_RESULT_DIRECTORY_NOT_A_DIRECTORY = "Result directory %s is not a directory.";
    private static final String EXPECTED_ERROR_MESSAGE_INVALID_DELIMITER = "Delimiter %s is not a single character.";

    private static final String TEST_RESOURCE_DIRECTORY = "src/test/resources/validators_resources/";
    private static final String TEST_PATH_EMPTY = "";
    private static final String TEST_PATH_DOES_NOT_EXIST = "abc";
    private static final String TEST_INPUT_FILE_NAME = "testInputFile.txt";
    private static final String TEST_VALID_INPUT_FILE = TEST_RESOURCE_DIRECTORY + TEST_INPUT_FILE_NAME;
    private static final String TEST_RESULT_DIRECTORY_NOT_A_DIRECTORY = TEST_RESOURCE_DIRECTORY + TEST_INPUT_FILE_NAME;
    private static final String TEST_VALID_DELIMITER = "|";
    private static final String TEST_INVALID_DELIMITER = "|,";

    private ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void beforeEach() throws IOException {

        System.setErr(new PrintStream(errContent));

    }

    @AfterEach
    public void afterEach() {

        System.setErr(originalErr);

    }

    private void resetErrContent() {

        errContent = new ByteArrayOutputStream();
        System.setErr(originalErr);
        System.setErr(new PrintStream(errContent));

    }

    /**
     * Unit tests the
     * {@link ColumnedDataValidator#validateDelimiter(String delimiter)} function.
     */
    @Test
    public void testDelimiterValidation() {

        // Run the validateDelimiter function and ensure the correct exception is thrown
        // for a delimiter that is more than 1 character long
        ColumnedDataPropertyException exceptionInvalidDelimiter = assertThrows(ColumnedDataPropertyException.class,
                () -> ColumnedDataValidator.validateDelimiter(TEST_INVALID_DELIMITER));
        assertEquals(String.format(EXPECTED_ERROR_MESSAGE_INVALID_DELIMITER, TEST_INVALID_DELIMITER),
                exceptionInvalidDelimiter.getMessage());

        // Run the validateDelimiter function and ensure no exception is thrown for a
        // delimiter that is 1 character in length
        assertDoesNotThrow(() -> ColumnedDataValidator.validateDelimiter(TEST_VALID_DELIMITER));

    }

    /**
     * Unit tests the
     * {@link ColumnedDataValidator#validateInputFile(String inputFile)} function.
     */
    @Test
    public void testInputFileValidation() {

        // Run the validateInputFile function and ensure the correct exception is thrown
        // for an inputFile that does not exist
        ColumnedDataPropertyException exceptionPathDoesNotExist = assertThrows(ColumnedDataPropertyException.class,
                () -> ColumnedDataValidator.validateInputFile(TEST_PATH_DOES_NOT_EXIST));
        assertEquals(String.format(EXPECTED_ERROR_MESSAGE_INPUT_FILE_DOES_NOT_EXIST, TEST_PATH_DOES_NOT_EXIST),
                exceptionPathDoesNotExist.getMessage());

        // Run the validateInputFile function and ensure the correct exception is thrown
        // for an inputFile pointing to an existing folder
        ColumnedDataPropertyException exceptionNotAFile = assertThrows(ColumnedDataPropertyException.class,
                () -> ColumnedDataValidator.validateInputFile(TEST_RESOURCE_DIRECTORY));
        assertEquals(String.format(EXPECTED_ERROR_MESSAGE_INPUT_FILE_NOT_A_FILE, TEST_RESOURCE_DIRECTORY),
                exceptionNotAFile.getMessage());

        // Run the validateInputFile function and ensure no exception is thrown for an
        // inputFile pointing to an existing txt file
        assertDoesNotThrow(() -> ColumnedDataValidator.validateInputFile(TEST_VALID_INPUT_FILE));

    }

    /**
     * Unit tests the
     * {@link ColumnedDataValidator#isResultDirectoryValid(String resultDirectory)}
     * function.
     */
    @Test
    public void testResultDirectoryValidation() {

        assertFalse(ColumnedDataValidator.isResultDirectoryValid(TEST_PATH_EMPTY));
        assertEquals(EXPECTED_ERROR_MESSAGE_RESULT_DIRECTORY_NOT_PROVIDED + NEW_LINE + NEW_LINE, errContent.toString());

        // Reset System.err contents
        resetErrContent();

        // Run the isResultDirectoryValid function and ensure the correct error message
        // is logged for a resultDirectory that does not exist
        assertFalse(ColumnedDataValidator.isResultDirectoryValid(TEST_PATH_DOES_NOT_EXIST));
        assertEquals(String.format(EXPECTED_ERROR_MESSAGE_RESULT_DIRECTORY_DOES_NOT_EXIST, TEST_PATH_DOES_NOT_EXIST)
                + NEW_LINE + NEW_LINE, errContent.toString());

        // Reset System.err contents
        resetErrContent();

        // Run the isResultDirectoryValid function and ensure the correct error message
        // is logged for a resultDirectory that points to a file
        assertFalse(ColumnedDataValidator.isResultDirectoryValid(TEST_RESULT_DIRECTORY_NOT_A_DIRECTORY));
        assertEquals(String.format(EXPECTED_ERROR_MESSAGE_RESULT_DIRECTORY_NOT_A_DIRECTORY,
                TEST_RESULT_DIRECTORY_NOT_A_DIRECTORY) + NEW_LINE + NEW_LINE, errContent.toString());

        // Run the validateResultDirectory function and ensure no exception is thrown
        // for a resultDirectory pointing to an existing folder
        assertTrue(ColumnedDataValidator.isResultDirectoryValid(TEST_RESOURCE_DIRECTORY));

    }

}