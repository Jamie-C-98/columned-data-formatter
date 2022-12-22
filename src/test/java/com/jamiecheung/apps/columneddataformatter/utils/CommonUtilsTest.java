package com.jamiecheung.apps.columneddataformatter.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Class to unit test the {@link CommonUtils} class.
 *
 * @author JamieCheung
 *
 */
class CommonUtilsTest {

    private static final String EXPECTED_GET_TIME_STAMP_AS_STRING = "19980302T083005";

    private static final ZonedDateTime TEST_ZONED_DATE_TIME = ZonedDateTime.of(1998, 3, 2, 8, 30, 5, 100,
            ZoneId.systemDefault());

    /**
     * Unit tests the
     * {@link CommonUtils#getTimeStampAsString(ZonedDateTime zonedDateTime)}
     * function.
     */
    @Test
    public void testGetTimeStampAsString() {

        String testGetTimeStampAsString = CommonUtils.getTimeStampAsString(TEST_ZONED_DATE_TIME);

        assertEquals(EXPECTED_GET_TIME_STAMP_AS_STRING, testGetTimeStampAsString);

    }

}