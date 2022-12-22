package com.jamiecheung.apps.columneddataformatter.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class to unit test the {@link FormatInputUtils} class.
 *
 * @author JamieCheung
 *
 */
class FormatInputUtilsTest {

    private static final String NEW_LINE = "\n";
    private static final String PRE_PROCESS_LINES_METHOD_NAME = "preProcessLines";
    private static final String FIND_MAX_DELIMITERS_A_LINE_METHOD_NAME = "findMaxDelimitersALine";
    private static final String REBALANCE_DELIMITER_METHOD_NAME = "rebalanceDelimiter";
    private static final String INSERT_PLACEHOLDER_BETWEEN_CONSECUTIVE_DELIMITERS_METHOD_NAME = "insertPlaceholderBetweenConsecutiveDelimiters";
    private static final String GET_COLUMN_LENGTHS_METHOD_NAME = "getColumnLengths";
    private static final String RESTRUCTURE_LINES_METHOD_NAME = "restructureLines";

    private static final String EXPECTED_FILE_CONTENTS_AS_A_STRING = "| First Name |   Surname |Email Address| Mobile Number   |    "
            + NEW_LINE + "    | Some one | 2ndName | a@b.com " + NEW_LINE + "  Someone | SecondName" + NEW_LINE
            + "    |Some1 |SomeName||   999|    ";
    private static final int EXPECTED_MAX_DELIMITERS_IN_A_LINE = 5;
    private static final int EXPECTED_LENGTH_COLUMN_1 = 12;
    private static final int EXPECTED_LENGTH_COLUMN_2 = 11;
    private static final int EXPECTED_LENGTH_COLUMN_3 = 13;
    private static final int EXPECTED_LENGTH_COLUMN_4 = 17;

    private static final String TEST_RESOURCE_DIRECTORY = "src/test/resources/utils_resources/";
    private static final int TEST_COLUMN_1_LENGTH = 10;
    private static final int TEST_COLUMN_2_LENGTH = 10;
    private static final int TEST_COLUMN_3_LENGTH = 13;
    private static final int TEST_COLUMN_4_LENGTH = 13;
    private static final int TEST_MAX_DELIMITERS_IN_A_LINE = 5;
    private static final String TEST_VALID_DELIMITER = "|";
    private static final String TEST_CONVERT_FILE_CONTENTS_TO_STRING_INPUT_FILE = TEST_RESOURCE_DIRECTORY
            + "testConvertFileContentsToStringInputFile.txt";
    private static final Path TEST_PRE_PROCESS_LINES_INPUT_FILE = Paths
            .get(TEST_RESOURCE_DIRECTORY + "testPreProcessLinesInputFile.txt");
    private static final Path TEST_PRE_PROCESS_LINES_EXPECTED_OUTPUT = Paths
            .get(TEST_RESOURCE_DIRECTORY + "testPreProcessLinesExpectedOutput.txt");
    private static final Path TEST_FIND_MAX_DELIMITERS_A_LINE_INPUT_FILE = Paths
            .get(TEST_RESOURCE_DIRECTORY + "testFindMaxDelimitersALineInputFile.txt");
    private static final Path TEST_REBALANCE_DELIMITER_INPUT_FILE = Paths
            .get(TEST_RESOURCE_DIRECTORY + "testRebalanceDelimiterInputFile.txt");
    private static final Path TEST_REBALANCE_DELIMITER_EXPECTED_OUTPUT = Paths
            .get(TEST_RESOURCE_DIRECTORY + "testRebalanceDelimiterExpectedOutput.txt");
    private static final Path TEST_INSERT_PLACEHOLDER_BETWEEN_CONSECUTIVE_DELIMITERS_INPUT_FILE = Paths
            .get(TEST_RESOURCE_DIRECTORY + "testInsertPlaceholderBetweenConsecutiveDelimitersInputFile.txt");
    private static final Path TEST_INSERT_PLACEHOLDER_BETWEEN_CONSECUTIVE_DELIMITERS_EXPECTED_OUTPUT = Paths
            .get(TEST_RESOURCE_DIRECTORY + "testInsertPlaceholderBetweenConsecutiveDelimitersExpectedOutput.txt");
    private static final Path TEST_GET_COLUMN_LENGTHS_INPUT_FILE = Paths
            .get(TEST_RESOURCE_DIRECTORY + "testGetColumnLengthsInputFile.txt");
    private static final Path TEST_RESTRUCTURE_LINES_INPUT_FILE = Paths
            .get(TEST_RESOURCE_DIRECTORY + "testRestructureLinesInputFile.txt");
    private static final Path TEST_RESTRUCTURE_LINES_EXPECTED_OUTPUT = Paths
            .get(TEST_RESOURCE_DIRECTORY + "testRestructureLinesExpectedOutput.txt");

    private static List<String> testLinesAsStrings = new ArrayList<>();
    private static List<StringBuilder> testLinesAsStringBuilders = new ArrayList<>();
    private static List<Integer> testColumnLengths = new ArrayList<>();

    private static List<String> expectedLinesAsStrings = new ArrayList<>();

    private static FormatInputUtils formatInputUtils = new FormatInputUtils();
    private static Method preProcessLinesMethod;
    private static Method findMaxDelimitersALineMethod;
    private static Method rebalanceDelimiterMethod;
    private static Method insertPlaceholderBetweenConsecutiveDelimitersMethod;
    private static Method getColumnLengthsMethod;
    private static Method restructureLinesMethod;

    @BeforeEach
    public void setUpBeforeEach() {

        // Reset the lists
        testLinesAsStrings = new ArrayList<>();
        testLinesAsStringBuilders = new ArrayList<>();
        testColumnLengths = new ArrayList<>();

    }

    /**
     * Unit tests the
     * {@link FormatInputUtils#convertFileContentsToString(String inputFile)}
     * function.
     * 
     * @throws IOException
     * 
     */
    @Test
    public void testConvertFileContentsToString() throws IOException {

        // Run the convertFileContentsToString function
        String testFileContentsAsAString = FormatInputUtils
                .convertFileContentsToString(TEST_CONVERT_FILE_CONTENTS_TO_STRING_INPUT_FILE);

        // Check the correct String is returned
        assertEquals(EXPECTED_FILE_CONTENTS_AS_A_STRING, testFileContentsAsAString);

    }

    /**
     * Unit tests the
     * {@link FormatInputUtils#preProcessLines(List linesAsStrings, String delimiter, List linesAsStringBuilders)
     * FormatInputUtils.preProcessLines(List&lt;String&gt; linesAsStrings, String
     * delimiter, List&lt;StringBuilder&gt; linesAsStringBuilders)} function.
     *
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IOException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    @Test
    public void testPreProcessLines() throws NoSuchMethodException, SecurityException, IOException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        preProcessLinesMethod = formatInputUtils.getClass().getDeclaredMethod(PRE_PROCESS_LINES_METHOD_NAME, List.class,
                String.class, List.class);
        preProcessLinesMethod.setAccessible(true);

        setLinesAsStrings(TEST_PRE_PROCESS_LINES_INPUT_FILE);

        // Run the preProcessLines function
        preProcessLinesMethod.invoke(null, testLinesAsStrings, TEST_VALID_DELIMITER, testLinesAsStringBuilders);

        setExpectedLinesAsStrings(TEST_PRE_PROCESS_LINES_EXPECTED_OUTPUT);

        checkLinesAsStringBuilders(expectedLinesAsStrings, testLinesAsStringBuilders);

        preProcessLinesMethod.setAccessible(false);

    }

    /**
     * Unit tests the
     * {@link FormatInputUtils#findMaxDelimitersALine(List linesAsStringBuilders, String delimiter)
     * FormatInputUtils.findMaxDelimitersALine(List&lt;StringBuilder&gt;
     * linesAsStringBuilders, String delimiter} function.
     *
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IOException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    @Test
    public void testFindMaxDelimitersALine() throws NoSuchMethodException, SecurityException, IOException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        findMaxDelimitersALineMethod = formatInputUtils.getClass()
                .getDeclaredMethod(FIND_MAX_DELIMITERS_A_LINE_METHOD_NAME, List.class, String.class);
        findMaxDelimitersALineMethod.setAccessible(true);

        setLinesAsStringBuilders(TEST_FIND_MAX_DELIMITERS_A_LINE_INPUT_FILE);

        // Run the findMaxDelimitersALine function
        int testFindMaxDelimitersALine = (Integer) findMaxDelimitersALineMethod.invoke(null, testLinesAsStringBuilders,
                TEST_VALID_DELIMITER);

        // Check the correct int is returned
        assertEquals(EXPECTED_MAX_DELIMITERS_IN_A_LINE, testFindMaxDelimitersALine);

        findMaxDelimitersALineMethod.setAccessible(false);

    }

    /**
     * Unit tests the
     * {@link FormatInputUtils#rebalanceDelimiter(List linesAsStringBuilders, String delimiter, int maxDelimiterCountInASingleLine)
     * FormatInputUtils.rebalanceDelimiter(List&lt;StringBuilder&gt;
     * linesAsStringBuilders, String delimiter, int maxDelimiterCountInASingleLine)}
     * function.
     *
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IOException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    @Test
    public void testRebalanceDelimiter() throws NoSuchMethodException, SecurityException, IOException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        rebalanceDelimiterMethod = formatInputUtils.getClass().getDeclaredMethod(REBALANCE_DELIMITER_METHOD_NAME,
                List.class, String.class, int.class);
        rebalanceDelimiterMethod.setAccessible(true);

        setLinesAsStringBuilders(TEST_REBALANCE_DELIMITER_INPUT_FILE);

        // Run the rebalanceDelimiter function
        rebalanceDelimiterMethod.invoke(null, testLinesAsStringBuilders, TEST_VALID_DELIMITER,
                TEST_MAX_DELIMITERS_IN_A_LINE);

        setExpectedLinesAsStrings(TEST_REBALANCE_DELIMITER_EXPECTED_OUTPUT);

        checkLinesAsStringBuilders(expectedLinesAsStrings, testLinesAsStringBuilders);

        rebalanceDelimiterMethod.setAccessible(false);

    }

    /**
     * Unit tests the
     * {@link FormatInputUtils#insertPlaceholderBetweenConsecutiveDelimiters(List linesAsStringBuilders, String delimiter)
     * FormatInputUtils.insertPlaceholderBetweenConsecutiveDelimiters(List&lt;StringBuilder&gt;
     * linesAsStringBuilders, String delimiter)} function.
     *
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IOException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    @Test
    public void testInsertPlaceholderBetweenConsecutiveDelimiters() throws NoSuchMethodException, SecurityException,
            IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        insertPlaceholderBetweenConsecutiveDelimitersMethod = formatInputUtils.getClass().getDeclaredMethod(
                INSERT_PLACEHOLDER_BETWEEN_CONSECUTIVE_DELIMITERS_METHOD_NAME, List.class, String.class);
        insertPlaceholderBetweenConsecutiveDelimitersMethod.setAccessible(true);

        setLinesAsStringBuilders(TEST_INSERT_PLACEHOLDER_BETWEEN_CONSECUTIVE_DELIMITERS_INPUT_FILE);

        // Run the insertPlaceholderBetweenConsecutiveDelimiters function
        insertPlaceholderBetweenConsecutiveDelimitersMethod.invoke(null, testLinesAsStringBuilders,
                TEST_VALID_DELIMITER);

        setExpectedLinesAsStrings(TEST_INSERT_PLACEHOLDER_BETWEEN_CONSECUTIVE_DELIMITERS_EXPECTED_OUTPUT);

        checkLinesAsStringBuilders(expectedLinesAsStrings, testLinesAsStringBuilders);

        insertPlaceholderBetweenConsecutiveDelimitersMethod.setAccessible(false);

    }

    /**
     * Unit tests the
     * {@link FormatInputUtils#getColumnLengths(int maxDelimiterCountInASingleLine, List linesAsStringBuilders, String delimiter, List columnLengths)
     * FormatInputUtils.getColumnLengths(int maxDelimiterCountInASingleLine,
     * List&lt;StringBuilder&gt; linesAsStringBuilders, String delimiter,
     * List&lt;Integer&gt; columnLengths)} function.
     *
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IOException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    @Test
    public void testGetColumnLengths() throws NoSuchMethodException, SecurityException, IOException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        getColumnLengthsMethod = formatInputUtils.getClass().getDeclaredMethod(GET_COLUMN_LENGTHS_METHOD_NAME,
                int.class, List.class, String.class, List.class);
        getColumnLengthsMethod.setAccessible(true);

        setLinesAsStringBuilders(TEST_GET_COLUMN_LENGTHS_INPUT_FILE);

        // Run the getColumnLengths function
        getColumnLengthsMethod.invoke(null, TEST_MAX_DELIMITERS_IN_A_LINE, testLinesAsStringBuilders,
                TEST_VALID_DELIMITER, testColumnLengths);

        // Check the testColumnLengths list populated correctly
        assertEquals(EXPECTED_LENGTH_COLUMN_1, testColumnLengths.get(0));
        assertEquals(EXPECTED_LENGTH_COLUMN_2, testColumnLengths.get(1));
        assertEquals(EXPECTED_LENGTH_COLUMN_3, testColumnLengths.get(2));
        assertEquals(EXPECTED_LENGTH_COLUMN_4, testColumnLengths.get(3));

        getColumnLengthsMethod.setAccessible(false);

    }

    /**
     * Unit tests the
     * {@link FormatInputUtils#restructureLines(List linesAsStringBuilders, String delimiter, List columnLengths)
     * FormatInputUtils.restructureLines(List&lt;StringBuilder&gt;
     * linesAsStringBuilders, String delimiter, List&lt;Integer&gt; columnLengths)}
     * function.
     *
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IOException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    @Test
    public void testRestructureLines() throws NoSuchMethodException, SecurityException, IOException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        restructureLinesMethod = formatInputUtils.getClass().getDeclaredMethod(RESTRUCTURE_LINES_METHOD_NAME,
                List.class, String.class, List.class);
        restructureLinesMethod.setAccessible(true);

        // Set the columnLengths list to control how the lines get restructured
        testColumnLengths = new ArrayList<>();
        testColumnLengths.add(TEST_COLUMN_1_LENGTH);
        testColumnLengths.add(TEST_COLUMN_2_LENGTH);
        testColumnLengths.add(TEST_COLUMN_3_LENGTH);
        testColumnLengths.add(TEST_COLUMN_4_LENGTH);

        setLinesAsStringBuilders(TEST_RESTRUCTURE_LINES_INPUT_FILE);

        // Run the restructureLines function
        restructureLinesMethod.invoke(null, testLinesAsStringBuilders, TEST_VALID_DELIMITER, testColumnLengths);

        setExpectedLinesAsStrings(TEST_RESTRUCTURE_LINES_EXPECTED_OUTPUT);

        checkLinesAsStringBuilders(expectedLinesAsStrings, testLinesAsStringBuilders);

        restructureLinesMethod.setAccessible(false);

    }

    /*
     * Sets the testLinesAsStrings to the lines of a test input file (the test
     * input)
     */
    private void setLinesAsStrings(Path inputFilePath) throws IOException {

        testLinesAsStrings = Files.readAllLines(inputFilePath);

    }

    /*
     * Sets the testLinesAsStrings and testLinesAsStringBuilders to the lines of am
     * input file (the test input)
     */
    private void setLinesAsStringBuilders(Path inputFilePath) throws IOException {

        setLinesAsStrings(inputFilePath);
        testLinesAsStringBuilders = new ArrayList<>();

        for (String line : testLinesAsStrings) {
            testLinesAsStringBuilders.add(new StringBuilder(line));
        }

    }

    /*
     * Sets the expectedLinesAsStrings to the lines of an input file (the expected
     * output)
     */
    private void setExpectedLinesAsStrings(Path expectedOutputFilePath) throws IOException {

        expectedLinesAsStrings = Files.readAllLines(expectedOutputFilePath);

    }

    /*
     * Compare the test input (linesAsStringBuilders) to the expected output
     * (expectedLinesAsStrings) line by line
     */
    private void checkLinesAsStringBuilders(List<String> expectedLinesAsStrings,
            List<StringBuilder> linesAsStringBuilders) {

        for (int lineNumber = 0; lineNumber < linesAsStringBuilders.size(); lineNumber++) {
            assertEquals(expectedLinesAsStrings.get(lineNumber), linesAsStringBuilders.get(lineNumber).toString());
        }

    }

}