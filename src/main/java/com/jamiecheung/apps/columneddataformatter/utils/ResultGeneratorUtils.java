package com.jamiecheung.apps.columneddataformatter.utils;

import static com.jamiecheung.apps.columneddataformatter.constants.CommonConstants.EMPTY_STRING;
import static com.jamiecheung.apps.columneddataformatter.constants.CommonConstants.END_OF_FORMATTED_DATA_CONTENTS;
import static com.jamiecheung.apps.columneddataformatter.constants.CommonConstants.FILE_EXTENSION_START_CHARACTER;
import static com.jamiecheung.apps.columneddataformatter.constants.CommonConstants.FILE_PATH_SEPARATOR_CHARACTERS;
import static com.jamiecheung.apps.columneddataformatter.constants.CommonConstants.NEW_LINE;
import static com.jamiecheung.apps.columneddataformatter.constants.CommonConstants.RESULT_FILE_SUFFIX;
import static com.jamiecheung.apps.columneddataformatter.constants.CommonConstants.START_OF_FORMATTED_DATA_CONTENTS;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Utility class for generating the transformed contents of the input.
 *
 * @author JamieCheung
 *
 */
public class ResultGeneratorUtils {

    /**
     * Outputs the formatted results to the console and generates a file containing
     * the resulting formatted contents.
     *
     * @param linesAsStringBuilders
     *                              the formatted lines
     * @param resultDirectory
     *                              the folder to create the resulting file
     *                              containing the linesAsStringBuilders
     * @param inputFile
     *                              the file containing the input text
     *
     * @throws IOException
     */
    public static void writeOutFormattedResults(List<StringBuilder> linesAsStringBuilders, String inputFile,
            String resultDirectory, boolean isResultDirectoryValid) throws IOException {

        outputResultsToConsole(linesAsStringBuilders);

        if (isResultDirectoryValid) {
            generateResultFile(inputFile, resultDirectory, linesAsStringBuilders);
        }

    }

    /*
     * Outputs the formatted results to the console.
     */
    private static void outputResultsToConsole(List<StringBuilder> linesAsStringBuilders) {

        System.out.println(START_OF_FORMATTED_DATA_CONTENTS);
        linesAsStringBuilders.stream().forEach(System.out::println);
        System.out.println(END_OF_FORMATTED_DATA_CONTENTS);

    }

    /*
     * Generates the file containing the resulting formatted contents.
     */
    private static void generateResultFile(String inputFile, String resultDirectory,
            List<StringBuilder> linesAsStringBuilders) throws IOException {

        Path inputFilePath = Paths.get(inputFile);

        String resultFileName = nameResultFile(inputFilePath);
        Path resultFilePath = createResultFile(resultDirectory, resultFileName);

        writeResultFile(resultFilePath, linesAsStringBuilders);

    }

    /*
     * Names the resulting file by appending a suffix onto the input file name.
     */
    private static String nameResultFile(Path inputFilePath) {

        StringBuilder inputFileName = new StringBuilder(inputFilePath.getFileName().toString());
        int suffixPosition = inputFileName.indexOf(getFileExtension(inputFilePath.getFileName().toString()));
        inputFileName.insert(suffixPosition, RESULT_FILE_SUFFIX);

        return inputFileName.toString();

    }

    /*
     * Creates the resulting file on the file system if it does not already exist.
     */
    private static Path createResultFile(String resultDirectory, String resultFileName) throws IOException {

        Path resultFilePath = Paths.get(resultDirectory, resultFileName);

        if (!Files.exists(resultFilePath)) {
            Files.createFile(resultFilePath);
        }

        return resultFilePath;

    }

    /*
     * Writes out the formatted lines onto the resulting file.
     */
    private static void writeResultFile(Path resultFilePath, List<StringBuilder> linesAsStringBuilders)
            throws IOException {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(resultFilePath.toFile()))) {

            for (int i = 0; i < linesAsStringBuilders.size(); i++) {

                writer.write(linesAsStringBuilders.get(i).toString());

                // Insert a new line for the next row of data, but do not add a new line after
                // the last row of data
                if (i != linesAsStringBuilders.size() - 1) {
                    writer.write(NEW_LINE);
                }

            }

        }

    }

    /*
     * Gets the file extension of a file in a file path, otherwise returns an empty
     * String.
     */
    private static String getFileExtension(String path) {

        String extension = EMPTY_STRING;

        int fileExtensionStart = path.lastIndexOf(FILE_EXTENSION_START_CHARACTER);
        int startOflastElementInPath = Math.max(path.lastIndexOf(FILE_PATH_SEPARATOR_CHARACTERS.get(0)),
                path.lastIndexOf(FILE_PATH_SEPARATOR_CHARACTERS.get(1)));

        if (fileExtensionStart > startOflastElementInPath) {
            extension = path.substring(fileExtensionStart);
        }

        return extension;

    }

}
