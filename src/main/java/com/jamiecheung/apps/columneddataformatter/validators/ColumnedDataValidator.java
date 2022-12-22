package com.jamiecheung.apps.columneddataformatter.validators;

import static com.jamiecheung.apps.columneddataformatter.constants.CommonConstants.DELIMITER_LENGTH_LIMIT;
import static com.jamiecheung.apps.columneddataformatter.constants.ErrorMessageConstants.ERROR_MESSAGE_DELIMITER_NOT_A_SINGLE_CHARACTER;
import static com.jamiecheung.apps.columneddataformatter.constants.ErrorMessageConstants.ERROR_MESSAGE_INPUT_FILE_DOES_NOT_EXIST;
import static com.jamiecheung.apps.columneddataformatter.constants.ErrorMessageConstants.ERROR_MESSAGE_INPUT_FILE_NOT_A_FILE;
import static com.jamiecheung.apps.columneddataformatter.constants.ErrorMessageConstants.ERROR_MESSAGE_RESULT_DIRECTORY_DOES_NOT_EXIST;
import static com.jamiecheung.apps.columneddataformatter.constants.ErrorMessageConstants.ERROR_MESSAGE_RESULT_DIRECTORY_NOT_A_DIRECTORY;
import static com.jamiecheung.apps.columneddataformatter.constants.ErrorMessageConstants.ERROR_MESSAGE_RESULT_DIRECTORY_NOT_PROVIDED;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.jamiecheung.apps.columneddataformatter.exceptions.ColumnedDataPropertyException;

/**
 * Class to validate constructor arguments for ColumnedData instance creation.
 *
 * @author JamieCheung
 *
 */
public class ColumnedDataValidator {

    /**
     * Checks the delimiter character is a single character.
     *
     * @param delimiter
     *                  the character separating each entry
     *
     * @throws ColumnedDataPropertyException
     *                                       if delimiter is more than 1 character
     *                                       in length
     */
    public static void validateDelimiter(String delimiter) throws ColumnedDataPropertyException {

        if (delimiter.length() > DELIMITER_LENGTH_LIMIT) {
            throw new ColumnedDataPropertyException(
                    String.format(ERROR_MESSAGE_DELIMITER_NOT_A_SINGLE_CHARACTER, delimiter));
        }

    }

    /**
     * Checks the inputFile String corresponds to an existing file system location
     * and is a file.
     *
     * @param inputFile
     *                  the path to the file that needs formatting
     *
     * @throws ColumnedDataPropertyException
     *                                       if inputFile does not exist or is not a
     *                                       file
     */
    public static void validateInputFile(String inputFile) throws ColumnedDataPropertyException {

        Path inputFilePath = Paths.get(inputFile);

        if (!Files.exists(inputFilePath)) {
            throw new ColumnedDataPropertyException(String.format(ERROR_MESSAGE_INPUT_FILE_DOES_NOT_EXIST, inputFile));
        } else if (Files.isDirectory(inputFilePath)) {
            throw new ColumnedDataPropertyException(String.format(ERROR_MESSAGE_INPUT_FILE_NOT_A_FILE, inputFile));
        }

    }

    /**
     * Checks the resultDirectory String corresponds to an existing file system
     * location and is a folder.
     * 
     * @param resultDirectory
     *                        the path to the folder to create the resulting
     *                        formatted file
     */
    public static boolean isResultDirectoryValid(String resultDirectory) {

        if (resultDirectory.isEmpty()) {
            System.err.println(ERROR_MESSAGE_RESULT_DIRECTORY_NOT_PROVIDED);
            System.err.println();
            return false;
        }

        Path resultDirectoryPath = Paths.get(resultDirectory);

        if (!Files.exists(resultDirectoryPath)) {
            System.err.println(String.format(ERROR_MESSAGE_RESULT_DIRECTORY_DOES_NOT_EXIST, resultDirectory));
            System.err.println();
            return false;
        } else if (!Files.isDirectory(resultDirectoryPath)) {
            System.err.println(String.format(ERROR_MESSAGE_RESULT_DIRECTORY_NOT_A_DIRECTORY, resultDirectory));
            System.err.println();
            return false;
        }

        return true;

    }

}
