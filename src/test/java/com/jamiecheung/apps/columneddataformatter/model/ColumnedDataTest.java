package com.jamiecheung.apps.columneddataformatter.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Class to unit test the {@link ColumnedData} class.
 *
 * @author JamieCheung
 *
 */
class ColumnedDataTest {

    private static final String NEW_LINE = "\n";

    private static final List<String> EXPECTED_LINES_AS_STRINGS = Arrays
            .asList(new String[] { "testContents", "testContents" });

    private static final ZonedDateTime TEST_CREATION_TIME_STAMP = ZonedDateTime.of(1998, 3, 2, 8, 30, 5, 100,
            ZoneId.systemDefault());
    private static final String TEST_DELIMITER = "testDelimiter";
    private static final String TEST_CONTENTS = "testContents" + NEW_LINE + "testContents";
    private static final String TEST_CONTENTS_TO_ADD = "test";

    private ColumnedData testColumnedData = new ColumnedData(TEST_CREATION_TIME_STAMP, TEST_DELIMITER, TEST_CONTENTS);

    /**
     * Unit tests the
     * {@link ColumnedData#ColumnedData(ZonedDateTime creationTimeStamp, String delimiter, String contents)}
     * constructor.
     */
    @Test
    public void testColumnedDataConstructor() {

        // Check object properties set correctly
        assertEquals(TEST_CREATION_TIME_STAMP, testColumnedData.getCreationTimeStamp());
        assertEquals(TEST_DELIMITER, testColumnedData.getDelimiter());
        assertEquals(TEST_CONTENTS, testColumnedData.getContents());
        assertEquals(EXPECTED_LINES_AS_STRINGS, testColumnedData.getLinesAsStrings());

        // Check optional object properties set to null
        assertNull(testColumnedData.getInputFile());
        assertNull(testColumnedData.getResultDirectory());

    }

    /**
     * Unit tests the {@link ColumnedData#getCreationTimeStamp()} function. Test the
     * {@link ColumnedData#creationTimeStamp} property is immutable.
     */
    @Test
    public void testGetCreationTimeStamp() {

        // Get creationTimeStamp and +1hr to it
        testColumnedData.getCreationTimeStamp().plusHours(1);

        // Check it did not +1hr to the original property
        assertEquals(TEST_CREATION_TIME_STAMP, testColumnedData.getCreationTimeStamp());

    }

    /**
     * Unit tests the {@link ColumnedData#getLinesAsStrings()} function. Test the
     * {@link ColumnedData#linesAsStrings} property is immutable.
     */
    @Test
    public void testGetLinesAsStrings() {

        // Get linesAsStrings and add an entry to the list
        testColumnedData.getLinesAsStrings().add(TEST_CONTENTS_TO_ADD);

        // Check it did not add entry to the original property
        assertEquals(EXPECTED_LINES_AS_STRINGS, testColumnedData.getLinesAsStrings());

    }

}