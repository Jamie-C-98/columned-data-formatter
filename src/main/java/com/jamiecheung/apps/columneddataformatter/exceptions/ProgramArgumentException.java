package com.jamiecheung.apps.columneddataformatter.exceptions;

import java.io.IOException;

/**
 * Class containing custom exception to be thrown in the case that invalid
 * arguments are passed to the main method of
 * {@link com.jamiecheung.apps.columneddataformatter.executors.Executor Executor}.
 *
 * @author JamieCheung
 *
 */
public class ProgramArgumentException extends IOException {

    public ProgramArgumentException(String message) {
        super(message);
    }

}
