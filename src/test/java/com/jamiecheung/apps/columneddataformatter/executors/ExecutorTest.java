package com.jamiecheung.apps.columneddataformatter.executors;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class to unit test the {@link Executor} class.
 *
 * @author JamieCheung
 *
 */
class ExecutorTest {

    private static final String EMPTY_STRING = "";
    private static final String NEW_LINE = "\n";
    private static final String START_OF_FORMATTED_DATA_CONTENTS = "---START OF FORMATTED DATA CONTENTS---";
    private static final String END_OF_FORMATTED_DATA_CONTENTS = "---END OF FORMATTED DATA CONTENTS---";

    private static final String EXPECTED_RESULTS_FILE_NAME = "testInputFile_formatted.txt";
    private static final String EXPECTED_TIME_STAMPED_RESULTS_FILE_NAME = ".*T.*_formatted.txt";
    private static final String EXPECTED_CONSOLE_ERROR = "Result directory not provided." + NEW_LINE + NEW_LINE;

    private static final String TEST_RESOURCE_DIRECTORY = "src/test/resources/executors_resources/";
    private static final Path TEST_RESOURCE_DIRECTORY_PATH = Paths.get(TEST_RESOURCE_DIRECTORY);
    private static final String TEST_DELIMITER = "|";
    private static final String TEST_INPUT_FILE_NAME = "testInputFile.txt";
    private static final String TEST_INPUT_FILE = TEST_RESOURCE_DIRECTORY + TEST_INPUT_FILE_NAME;
    private static final String[] TEST_ARGS = { TEST_DELIMITER, TEST_INPUT_FILE, TEST_RESOURCE_DIRECTORY };
    private static final String[] TEST_ARGS_NO_RESULT_DIRECTORY = { TEST_DELIMITER, TEST_INPUT_FILE, EMPTY_STRING };
    private static final String TEST_RESULT_FILE = TEST_RESOURCE_DIRECTORY + EXPECTED_RESULTS_FILE_NAME;
    private static final Path TEST_RESULT_FILE_PATH = Paths.get(TEST_RESULT_FILE);
    private static final Path TEST_OUTPUT_PATH = Paths.get(TEST_RESOURCE_DIRECTORY, "testOutput.txt");

    private static String[] testArgsInputString;
    private static String[] testArgsInputStringNoResultDirectory;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeAll
    public static void beforeAll() throws IOException {

        // Initialise test arguments (where arguments include a valid resultDirectory)
        testArgsInputString = new String[] { TEST_DELIMITER, Files.readAllLines(TEST_OUTPUT_PATH).stream()
                .map(Object::toString).collect(Collectors.joining(NEW_LINE)) + NEW_LINE, TEST_RESOURCE_DIRECTORY };

        // Initialise test arguments (where arguments do not include resultDirectory)
        testArgsInputStringNoResultDirectory = new String[] { TEST_DELIMITER, Files.readAllLines(TEST_OUTPUT_PATH)
                .stream().map(Object::toString).collect(Collectors.joining(NEW_LINE)) + NEW_LINE, EMPTY_STRING };

    }

    @BeforeEach
    public void beforeEach() throws IOException {

        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        // Delete any result file before the test begins
        deleteGeneratedResultFiles();

    }

    @AfterEach
    public void afterEach() {

        System.setOut(originalOut);
        System.setErr(originalErr);

    }

    @AfterAll
    public static void afterAll() throws IOException {

        // Delete any result file created by the last test case
        deleteGeneratedResultFiles();

    }

    /**
     * Unit tests the {@link Executor#main(String[] args)} function.
     *
     * @throws IOException
     */
    @Test
    public void testMainInputFile() throws IOException {

        // Run the main function
        Executor.main(TEST_ARGS);

        byte[] resultFileBytes = Files.readAllBytes(TEST_RESULT_FILE_PATH);
        byte[] expectedFileBytes = Files.readAllBytes(TEST_OUTPUT_PATH);

        // Check result file with correct contents is created
        assertArrayEquals(expectedFileBytes, resultFileBytes);

        // Check the console output
        String expectedConsoleOutput = START_OF_FORMATTED_DATA_CONTENTS + NEW_LINE + Files
                .readAllLines(TEST_OUTPUT_PATH).stream().map(Object::toString).collect(Collectors.joining(NEW_LINE))
                + NEW_LINE + END_OF_FORMATTED_DATA_CONTENTS + NEW_LINE;
        assertEquals(expectedConsoleOutput, outContent.toString());

    }

    /**
     * Unit tests the {@link Executor#main(String[] args)} function with input
     * parameters containing an input file and result directory.
     *
     * @throws IOException
     */
    @Test
    public void testMainInputFileNoResultDirectory() throws IOException {

        // Run the main function
        Executor.main(TEST_ARGS_NO_RESULT_DIRECTORY);

        // Check console error
        assertEquals(EXPECTED_CONSOLE_ERROR, errContent.toString());

        // Check no result file is created
        if (Files.exists(TEST_RESULT_FILE_PATH)) {
            Assertions.fail();
        }

        // Check the console output
        String expectedConsoleOutput = START_OF_FORMATTED_DATA_CONTENTS + NEW_LINE + Files
                .readAllLines(TEST_OUTPUT_PATH).stream().map(Object::toString).collect(Collectors.joining(NEW_LINE))
                + NEW_LINE + END_OF_FORMATTED_DATA_CONTENTS + NEW_LINE;
        assertEquals(expectedConsoleOutput, outContent.toString());

    }

    /**
     * Unit tests the {@link Executor#main(String[] args)} function with input
     * parameters containing an input String and result directory.
     *
     * @throws IOException
     */
    @Test
    public void testMainInputString() throws IOException {

        // Run the main function
        Executor.main(testArgsInputString);

        Path timeStampedResultFile = Files
                .find(TEST_RESOURCE_DIRECTORY_PATH, 1,
                        (path, attributes) -> path.toString().matches(EXPECTED_TIME_STAMPED_RESULTS_FILE_NAME))
                .findFirst().get();

        byte[] resultFileBytes = Files.readAllBytes(timeStampedResultFile);
        byte[] expectedFileBytes = Files.readAllBytes(TEST_OUTPUT_PATH);

        // Check result file with correct contents is created
        assertArrayEquals(expectedFileBytes, resultFileBytes);

        // Check the console output
        String expectedConsoleOutput = START_OF_FORMATTED_DATA_CONTENTS + NEW_LINE + Files
                .readAllLines(TEST_OUTPUT_PATH).stream().map(Object::toString).collect(Collectors.joining(NEW_LINE))
                + NEW_LINE + END_OF_FORMATTED_DATA_CONTENTS + NEW_LINE;
        assertEquals(expectedConsoleOutput, outContent.toString());

    }

    /**
     * Unit tests the {@link Executor#main(String[] args)} function with input
     * parameters containing an input String and no result directory.
     *
     * @throws IOException
     */
    @Test
    public void testMainInputStringNoResultDirectory() throws IOException {

        // Run the main function
        Executor.main(testArgsInputStringNoResultDirectory);

        // Check console error
        assertEquals(EXPECTED_CONSOLE_ERROR, errContent.toString());

        // Check no result file is created
        if (Files.exists(TEST_RESULT_FILE_PATH)) {
            Assertions.fail();
        }

        // Check the console output
        String expectedConsoleOutput = START_OF_FORMATTED_DATA_CONTENTS + NEW_LINE + Files
                .readAllLines(TEST_OUTPUT_PATH).stream().map(Object::toString).collect(Collectors.joining(NEW_LINE))
                + NEW_LINE + END_OF_FORMATTED_DATA_CONTENTS + NEW_LINE;
        assertEquals(expectedConsoleOutput, outContent.toString());

    }

    /*
     * Deletes result files generated when calling Executor's main method
     */
    private static void deleteGeneratedResultFiles() throws IOException {

        // Delete result file with path TEST_RESULT_FILE
        Files.deleteIfExists(TEST_RESULT_FILE_PATH);

        // Delete time-stamped result files
        Files.find(TEST_RESOURCE_DIRECTORY_PATH, 1,
                (path, attributes) -> path.toString().matches(EXPECTED_TIME_STAMPED_RESULTS_FILE_NAME)).forEach(t -> {
                    try {
                        Files.deleteIfExists(t);
                    } catch (IOException e) {
                        // Do nothing
                    }
                });

    }

}