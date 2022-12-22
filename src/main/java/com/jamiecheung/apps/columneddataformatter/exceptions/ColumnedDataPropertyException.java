package com.jamiecheung.apps.columneddataformatter.exceptions;

import java.io.IOException;

/**
 * Class containing custom exception to be thrown in the case that a
 * {@link com.jamiecheung.apps.columneddataformatter.model.ColumnedData ColumnedData} instance has
 * invalid properties.
 *
 * @author JamieCheung
 *
 */
public class ColumnedDataPropertyException extends IOException {

    public ColumnedDataPropertyException(String message) {
        super(message);
    }

}
