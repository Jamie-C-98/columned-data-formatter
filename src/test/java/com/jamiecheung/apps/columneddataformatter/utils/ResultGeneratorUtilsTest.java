package com.jamiecheung.apps.columneddataformatter.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class to unit test the {@link ResultGeneratorUtils} class.
 *
 * @author JamieCheung
 *
 */
class ResultGeneratorUtilsTest {

    private static final String EMPTY_STRING = "";
    private static final String NEW_LINE = "\n";
    private static final String START_OF_FORMATTED_DATA_CONTENTS = "---START OF FORMATTED DATA CONTENTS---";
    private static final String END_OF_FORMATTED_DATA_CONTENTS = "---END OF FORMATTED DATA CONTENTS---";
    private static final String OUTPUT_RESULTS_TO_CONSOLE_METHOD_NAME = "outputResultsToConsole";
    private static final String NAME_RESULT_FILE_METHOD_NAME = "nameResultFile";
    private static final String GET_FILE_EXTENSION_METHOD_NAME = "getFileExtension";
    private static final String CREATE_RESULT_FILE_METHOD_NAME = "createResultFile";
    private static final String WRITE_RESULT_FILE_METHOD_NAME = "writeResultFile";

    private static final String EXPECTED_RESULTS_FILE_NAME = "testInputFile_formatted.txt";
    private static final String EXPECTED_FILE_EXTENSION = ".txt";

    private static final String TEST_STRING_BUILDER_STRING_1 = "testStringBuilder1";
    private static final String TEST_STRING_BUILDER_STRING_2 = "testStringBuilder2";
    private static final String TEST_STRING_BUILDER_STRING_3 = "testStringBuilder3";
    private static final String TEST_RESOURCE_DIRECTORY = "src/test/resources/utils_resources/";
    private static final String TEST_INPUT_FILE_NAME = "testInputFile.txt";
    private static final String TEST_VALID_INPUT_FILE = TEST_RESOURCE_DIRECTORY + TEST_INPUT_FILE_NAME;
    private static final String TEST_STRING_FILE_PATH = "/a/b/c/test.txt";
    private static final String TEST_RESULT_FILE_NAME = "testWriteResultFile.txt";
    private static final String TEST_LINE_1 = "test line 1";
    private static final String TEST_LINE_2 = "test line 2";
    private static final String TEST_LINE_3 = "test line 3";
    private static final String TEST_LINE_4 = "test line 4";

    private static List<StringBuilder> testLinesAsStringBuilders = new ArrayList<>();

    private static ResultGeneratorUtils resultGeneratorUtils = new ResultGeneratorUtils();

    private static Method outputResultsToConsoleMethod;
    private static Method nameResultFileMethod;
    private static Method createResultFileMethod;
    private static Method writeResultFileMethod;
    private static Method getFileExtensionMethod;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpBeforeEach() {

        // Reset the lists
        testLinesAsStringBuilders = new ArrayList<>();

        System.setOut(new PrintStream(outContent));

    }

    @AfterEach
    public void afterEach() {

        System.setOut(originalOut);

    }

    /**
     * Unit tests the
     * {@link ResultGeneratorUtils#outputResultsToConsole(List linesAsStringBuilders)
     * ResultGeneratorUtils.outputResultsToConsole(List&lt;StringBuilder&gt;
     * linesAsStringBuilders)} function.
     * 
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    @Test
    public void testOutputResultsToConsole() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        outputResultsToConsoleMethod = resultGeneratorUtils.getClass()
                .getDeclaredMethod(OUTPUT_RESULTS_TO_CONSOLE_METHOD_NAME, List.class);
        outputResultsToConsoleMethod.setAccessible(true);

        testLinesAsStringBuilders.add(new StringBuilder(TEST_STRING_BUILDER_STRING_1));
        testLinesAsStringBuilders.add(new StringBuilder(TEST_STRING_BUILDER_STRING_2));
        testLinesAsStringBuilders.add(new StringBuilder(TEST_STRING_BUILDER_STRING_3));

        // Run the outputResultsToConsole function
        outputResultsToConsoleMethod.invoke(null, testLinesAsStringBuilders);

        // Check the console output
        String expectedConsoleOutput = START_OF_FORMATTED_DATA_CONTENTS + NEW_LINE + TEST_STRING_BUILDER_STRING_1
                + NEW_LINE + TEST_STRING_BUILDER_STRING_2 + NEW_LINE + TEST_STRING_BUILDER_STRING_3 + NEW_LINE
                + END_OF_FORMATTED_DATA_CONTENTS + NEW_LINE;
        assertEquals(expectedConsoleOutput, outContent.toString());

        outputResultsToConsoleMethod.setAccessible(false);

    }

    /**
     * Unit tests the
     * {@link ResultGeneratorUtils#nameResultFile(Path inputFilePath)} function.
     *
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    @Test
    public void testNameResultFile() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        nameResultFileMethod = resultGeneratorUtils.getClass().getDeclaredMethod(NAME_RESULT_FILE_METHOD_NAME,
                Path.class);
        nameResultFileMethod.setAccessible(true);

        // Run the nameResultFile function
        String testNameResultFile = (String) nameResultFileMethod.invoke(null, Paths.get(TEST_VALID_INPUT_FILE));

        // Check the correct String is returned
        assertEquals(EXPECTED_RESULTS_FILE_NAME, testNameResultFile);

        nameResultFileMethod.setAccessible(false);

    }

    /**
     * Unit tests the
     * {@link ResultGeneratorUtils#createResultFile(String resultDirectory, String resultFileName)}
     * function.
     *
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IOException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     *
     */
    @Test
    public void testCreateResultFile() throws NoSuchMethodException, SecurityException, IOException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        createResultFileMethod = resultGeneratorUtils.getClass().getDeclaredMethod(CREATE_RESULT_FILE_METHOD_NAME,
                String.class, String.class);
        createResultFileMethod.setAccessible(true);

        // Delete any result file before the test begins
        Path resultFilePath = Paths.get(TEST_RESOURCE_DIRECTORY, EXPECTED_RESULTS_FILE_NAME);
        Files.deleteIfExists(resultFilePath);

        // Run the createResultFile function
        createResultFileMethod.invoke(null, TEST_RESOURCE_DIRECTORY, EXPECTED_RESULTS_FILE_NAME);

        // Check the result file with the correct name is created
        assertTrue(Files.exists(resultFilePath));

        createResultFileMethod.setAccessible(false);

    }

    /**
     * Unit tests the
     * {@link ResultGeneratorUtils#writeResultFile(Path resultFilePath, List linesAsStringBuilders)
     * ResultGeneratorUtils.writeResultFile(Path resultFilePath,
     * List&lt;StringBuilder&gt; linesAsStringBuilders)} function.
     *
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IOException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    @Test
    public void testWriteResultFile() throws NoSuchMethodException, SecurityException, IOException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        writeResultFileMethod = resultGeneratorUtils.getClass().getDeclaredMethod(WRITE_RESULT_FILE_METHOD_NAME,
                Path.class, List.class);
        writeResultFileMethod.setAccessible(true);

        // Empty contents of resultFilePath before the test begins
        Path resultFilePath = Paths.get(TEST_RESOURCE_DIRECTORY, TEST_RESULT_FILE_NAME);
        PrintWriter writer = new PrintWriter(new FileWriter(resultFilePath.toFile()));
        writer.print(EMPTY_STRING);
        writer.close();

        // Set the linesAsStringBuilders list to control which lines are written out
        testLinesAsStringBuilders = new ArrayList<>();
        testLinesAsStringBuilders.add(new StringBuilder(TEST_LINE_1));
        testLinesAsStringBuilders.add(new StringBuilder(TEST_LINE_2));
        testLinesAsStringBuilders.add(new StringBuilder(TEST_LINE_3));
        testLinesAsStringBuilders.add(new StringBuilder(TEST_LINE_4));

        // Run the writeResultFile function
        writeResultFileMethod.invoke(null, resultFilePath, testLinesAsStringBuilders);

        // Read each line of resultFilePath and check each line is equal to TEST_LINE_X
        List<String> resultFileLinesAsStrings = Files.readAllLines(resultFilePath);
        assertEquals(TEST_LINE_1, resultFileLinesAsStrings.get(0));
        assertEquals(TEST_LINE_2, resultFileLinesAsStrings.get(1));
        assertEquals(TEST_LINE_3, resultFileLinesAsStrings.get(2));
        assertEquals(TEST_LINE_4, resultFileLinesAsStrings.get(3));

        writeResultFileMethod.setAccessible(false);

    }

    /**
     * Unit tests the {@link ResultGeneratorUtils#getFileExtension(String path)}
     * function.
     * 
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     *
     */
    @Test
    public void testGetFileExtension() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        getFileExtensionMethod = resultGeneratorUtils.getClass().getDeclaredMethod(GET_FILE_EXTENSION_METHOD_NAME,
                String.class);
        getFileExtensionMethod.setAccessible(true);

        // Run the getFileExtension function
        String testGetFileExtension = (String) getFileExtensionMethod.invoke(null, TEST_STRING_FILE_PATH);

        // Check the correct String is returned
        assertEquals(EXPECTED_FILE_EXTENSION, testGetFileExtension);

        getFileExtensionMethod.setAccessible(false);

    }

}