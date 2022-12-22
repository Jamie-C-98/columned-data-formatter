package com.jamiecheung.apps.columneddataformatter.utils;

import static com.jamiecheung.apps.columneddataformatter.constants.CommonConstants.DATE_TIME_SEPARATOR;
import static com.jamiecheung.apps.columneddataformatter.constants.CommonConstants.POSITION_OF_DATE_TIME_SEPARATOR;
import static com.jamiecheung.apps.columneddataformatter.constants.CommonConstants.TIME_STAMP_FORMAT;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for common functions.
 * 
 * @author JamieCheung
 *
 */
public class CommonUtils {

    /**
     * Converts zonedDateTime to a human-readable string.
     * 
     * @param zonedDateTime
     *                      the ZonedDateTime object to be converted
     * 
     * @return String representation of zonedDateTime
     */
    public static String getTimeStampAsString(ZonedDateTime zonedDateTime) {

        return new StringBuilder(zonedDateTime.format(DateTimeFormatter.ofPattern(TIME_STAMP_FORMAT)))
                .insert(POSITION_OF_DATE_TIME_SEPARATOR, DATE_TIME_SEPARATOR).toString();

    }

}