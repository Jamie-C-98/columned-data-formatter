package com.jamiecheung.apps.columneddataformatter.executors;

import static com.jamiecheung.apps.columneddataformatter.constants.CommonConstants.EMPTY_STRING;
import static com.jamiecheung.apps.columneddataformatter.constants.CommonConstants.TXT_FILE_EXTENSION;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import com.jamiecheung.apps.columneddataformatter.exceptions.ColumnedDataPropertyException;
import com.jamiecheung.apps.columneddataformatter.exceptions.ProgramArgumentException;
import com.jamiecheung.apps.columneddataformatter.model.ColumnedData;
import com.jamiecheung.apps.columneddataformatter.utils.CommonUtils;
import com.jamiecheung.apps.columneddataformatter.utils.FormatInputUtils;
import com.jamiecheung.apps.columneddataformatter.utils.ResultGeneratorUtils;
import com.jamiecheung.apps.columneddataformatter.validators.ArgumentsValidator;
import com.jamiecheung.apps.columneddataformatter.validators.ColumnedDataValidator;

/**
 * Class that executes all the functions to transform and output the contents
 * from an input file or String.
 *
 * @author JamieCheung
 *
 */
public class Executor {

    /**
     * The entry point for the columned-data-formatter application. Executes all the
     * functions to transform and output the contents from an input file or input
     * String.
     *
     * @param args
     *             The command line arguments
     * 
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        // Validate program arguments
        ArgumentsValidator.validateArguments(args);
        String delimiter = args[0];
        String toBeDeterminedArg = args[1];
        String contents = EMPTY_STRING;
        String inputFile = EMPTY_STRING;
        String resultDirectory = args[2];
        ColumnedDataValidator.validateDelimiter(delimiter);

        // Get current time stamp to associate with a ColumnedData object
        ZonedDateTime currentTimeStamp = ZonedDateTime.now(ZoneId.systemDefault());

        // Depending on whether contents represented as an input file or input String,
        // format it accordingly
        if (ArgumentsValidator.isArgumentAPath(toBeDeterminedArg)) {
            executeWithInputFile(currentTimeStamp, delimiter, toBeDeterminedArg, contents, inputFile, resultDirectory);
        } else {
            executeWithInputString(currentTimeStamp, delimiter, toBeDeterminedArg, contents, inputFile,
                    resultDirectory);
        }

    }

    /*
     * Interprets the provided toBeDeterminedArg to be an input file path. Validates
     * and extracts the contents from this file path, before executing the function
     * to format the contents.
     */
    private static void executeWithInputFile(ZonedDateTime currentTimeStamp, String delimiter, String toBeDeterminedArg,
            String contents, String inputFile, String resultDirectory) throws IOException {

        inputFile = toBeDeterminedArg;
        ColumnedDataValidator.validateInputFile(inputFile);

        contents = FormatInputUtils.convertFileContentsToString(inputFile);

        executeColumnedDataFormatting(currentTimeStamp, delimiter, contents, inputFile, resultDirectory);

    }

    /*
     * Interprets the provided toBeDeterminedArg to be a String. Creates a
     * placeholder input file name based on the current time stamp, before executing
     * the function to format the contents.
     */
    private static void executeWithInputString(ZonedDateTime currentTimeStamp, String delimiter,
            String toBeDeterminedArg, String contents, String inputFile, String resultDirectory) throws IOException {

        String timeStamp = CommonUtils.getTimeStampAsString(currentTimeStamp);
        inputFile = timeStamp + TXT_FILE_EXTENSION;

        contents = toBeDeterminedArg;

        executeColumnedDataFormatting(currentTimeStamp, delimiter, contents, inputFile, resultDirectory);

    }

    /*
     * Initialises the ColumnedData object and performs the formatting based on the
     * properties of the ColumnedData instance.
     */
    private static void executeColumnedDataFormatting(ZonedDateTime currentTimeStamp, String delimiter, String contents,
            String inputFile, String resultDirectory) throws IOException {

        // Initialise the object containing the useful properties
        ColumnedData columnedData = new ColumnedData(currentTimeStamp, delimiter, contents);
        columnedData.setInputFile(inputFile);
        columnedData.setResultDirectory(resultDirectory);

        // Perform formatting
        List<StringBuilder> linesAsStringBuilders = FormatInputUtils.formatInputFile(columnedData.getLinesAsStrings(),
                columnedData.getDelimiter());

        // Write out the results
        ResultGeneratorUtils.writeOutFormattedResults(linesAsStringBuilders, columnedData.getInputFile(),
                columnedData.getResultDirectory(),
                ColumnedDataValidator.isResultDirectoryValid(columnedData.getResultDirectory()));

    }

}