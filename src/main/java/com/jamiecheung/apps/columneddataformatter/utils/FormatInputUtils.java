package com.jamiecheung.apps.columneddataformatter.utils;

import static com.jamiecheung.apps.columneddataformatter.constants.CommonConstants.EMPTY_STRING;
import static com.jamiecheung.apps.columneddataformatter.constants.CommonConstants.NEW_LINE;
import static com.jamiecheung.apps.columneddataformatter.constants.CommonConstants.SPACE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Utility class for transforming the contents of the input file or input
 * String.
 *
 * @author JamieCheung
 *
 */
public class FormatInputUtils {

    /**
     * Converts the text in the input file to a String.
     * 
     * @param inputFile
     *                  the file containing the input text
     * 
     * @return the String corresponding to the contents of the inputFile
     * 
     * @throws IOException
     */
    public static String convertFileContentsToString(String inputFile) throws IOException {

        Path inputFilePath = Paths.get(inputFile);
        String fileContentsAsAString = EMPTY_STRING;

        fileContentsAsAString = Files.readAllLines(inputFilePath).stream().map(Object::toString)
                .collect(Collectors.joining(NEW_LINE));

        return fileContentsAsAString;
    }

    /**
     * Formats the contents of the input.
     *
     * @param linesAsStrings
     *                       the list of lines in the file that needs formatting
     * @param delimiter
     *                       the character that forms the columns of the data in the
     *                       file that needs formatting
     * 
     * @return the list of formatted lines
     */
    public static List<StringBuilder> formatInputFile(List<String> linesAsStrings, String delimiter) {

        List<StringBuilder> linesAsStringBuilders = new ArrayList<>();
        List<Integer> columnLengths = new ArrayList<>();

        preProcessLines(linesAsStrings, delimiter, linesAsStringBuilders);

        int maxDelimiterCountInASingleLine = findMaxDelimitersALine(linesAsStringBuilders, delimiter);

        rebalanceDelimiter(linesAsStringBuilders, delimiter, maxDelimiterCountInASingleLine);

        insertPlaceholderBetweenConsecutiveDelimiters(linesAsStringBuilders, delimiter);

        getColumnLengths(maxDelimiterCountInASingleLine, linesAsStringBuilders, delimiter, columnLengths);

        restructureLines(linesAsStringBuilders, delimiter, columnLengths);

        return linesAsStringBuilders;

    }

    /*
     * Removes excess characters in each line of the input and ensures every line
     * starts and ends with a delimiter.
     */
    private static void preProcessLines(List<String> linesAsStrings, String delimiter,
            List<StringBuilder> linesAsStringBuilders) {

        for (String line : linesAsStrings) {

            // Remove white spaces before first and after last non-space character
            line = line.trim();

            if (!line.isEmpty()) {

                // Insert a delimiter at the start of the line if it is not the first character
                if (line.charAt(0) != delimiter.charAt(0)) {
                    line = delimiter + line;
                }

                String[] entries = line.split(Pattern.quote(delimiter));

                // Remove white spaces before and after each entry
                for (int i = 0; i < entries.length; i++) {
                    entries[i] = entries[i].trim();
                }

                // Clear the line
                line = EMPTY_STRING;

                // Reconstruct the line with cleaned up entries
                for (String entry : entries) {
                    line = line.concat(entry + delimiter);
                }

                // Convert the Strings into StringBuilders
                linesAsStringBuilders.add(new StringBuilder(line));

            }

        }

    }

    /*
     * Finds the most delimiter occurrences in a single line.
     */
    private static int findMaxDelimitersALine(List<StringBuilder> linesAsStringBuilders, String delimiter) {

        int maxDelimiterCountInASingleLine = 0;

        for (StringBuilder line : linesAsStringBuilders) {

            int delimiterOccurrences = (int) line.chars().filter(ch -> ch == delimiter.charAt(0)).count();

            if (delimiterOccurrences > maxDelimiterCountInASingleLine) {
                maxDelimiterCountInASingleLine = delimiterOccurrences;
            }

        }

        return maxDelimiterCountInASingleLine;

    }

    /*
     * Ensures each line has an equal number (maxDelimiterCountInASingleLine) of
     * delimiter occurrences.
     */
    private static void rebalanceDelimiter(List<StringBuilder> linesAsStringBuilders, String delimiter,
            int maxDelimiterCountInASingleLine) {

        for (StringBuilder line : linesAsStringBuilders) {

            int delimiterOccurrences = (int) line.chars().filter(ch -> ch == delimiter.charAt(0)).count();

            while (delimiterOccurrences < maxDelimiterCountInASingleLine) {
                line.append(delimiter);
                delimiterOccurrences++;
            }

        }

    }

    /*
     * Inserts a space between consecutive delimiter characters in each line (this
     * is done to to enable String's split API to work as intended).
     */
    private static void insertPlaceholderBetweenConsecutiveDelimiters(List<StringBuilder> linesAsStringBuilders,
            String delimiter) {

        for (StringBuilder line : linesAsStringBuilders) {

            for (int i = 0; i < line.length() - 1; i++) {

                if (line.charAt(i) == delimiter.charAt(0) && line.charAt(i + 1) == delimiter.charAt(0)) {
                    line.insert(i + 1, SPACE);
                }

            }

        }

    }

    /*
     * Gets the longest character counts in each column and stores them.
     */
    private static void getColumnLengths(int maxDelimiterCountInASingleLine, List<StringBuilder> linesAsStringBuilders,
            String delimiter, List<Integer> columnLengths) {

        for (int column = 1; column < maxDelimiterCountInASingleLine; column++) {

            int longestStringInColumn = 0;

            for (StringBuilder linesAsStringBuilder : linesAsStringBuilders) {

                String columnEntry = linesAsStringBuilder.toString().split(Pattern.quote(delimiter))[column];

                if (columnEntry.length() > longestStringInColumn) {
                    longestStringInColumn = columnEntry.length();
                }

            }

            columnLengths.add(longestStringInColumn);

        }

    }

    /*
     * Ensures character lengths between each delimiter are consistent from line to
     * line (i.e. column formation).
     */
    private static void restructureLines(List<StringBuilder> linesAsStringBuilders, String delimiter,
            List<Integer> columnLengths) {

        for (StringBuilder line : linesAsStringBuilders) {

            String[] entries = line.toString().split(Pattern.quote(delimiter));

            // Update the lengths of shorter Strings (after the first delimiter) by
            // appending spaces to the end of them
            for (int i = 1; i < entries.length; i++) {

                while (entries[i].length() < columnLengths.get(i - 1)) {
                    entries[i] = entries[i].concat(SPACE);
                }

            }

            // Reset the StringBuilder
            line.setLength(0);

            // Reconstruct the line with cleaned up entries
            for (int i = 1; i < entries.length; i++) {

                if (i == 1) {
                    line.append(delimiter);
                }
                line.append(SPACE);
                line.append(entries[i]);
                line.append(SPACE);
                line.append(delimiter);

            }

        }

    }

}
