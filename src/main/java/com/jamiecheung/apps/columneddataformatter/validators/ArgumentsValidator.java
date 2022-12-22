package com.jamiecheung.apps.columneddataformatter.validators;

import static com.jamiecheung.apps.columneddataformatter.constants.CommonConstants.ACTUAL_ARGS_LENGTH_ONE;
import static com.jamiecheung.apps.columneddataformatter.constants.CommonConstants.ACTUAL_ARGS_LENGTH_TWO;
import static com.jamiecheung.apps.columneddataformatter.constants.CommonConstants.ACTUAL_ARGS_LENGTH_ZERO;
import static com.jamiecheung.apps.columneddataformatter.constants.CommonConstants.COMMA_SPACE;
import static com.jamiecheung.apps.columneddataformatter.constants.CommonConstants.EMPTY_STRING;
import static com.jamiecheung.apps.columneddataformatter.constants.CommonConstants.EXPECTED_ARGS_LENGTH;
import static com.jamiecheung.apps.columneddataformatter.constants.CommonConstants.EXPECTED_ARG_1;
import static com.jamiecheung.apps.columneddataformatter.constants.CommonConstants.EXPECTED_ARG_2;
import static com.jamiecheung.apps.columneddataformatter.constants.CommonConstants.EXPECTED_ARG_3;
import static com.jamiecheung.apps.columneddataformatter.constants.ErrorMessageConstants.ERROR_MESSAGE_NOT_ENOUGH_ARGS;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.jamiecheung.apps.columneddataformatter.exceptions.ProgramArgumentException;

/**
 * Class to validate the arguments of the program.
 *
 * @author JamieCheung
 *
 */
public class ArgumentsValidator {

    /**
     * Checks the number of arguments provided is at least
     * {@link com.jamiecheung.apps.columneddataformatter.constants.CommonConstants#EXPECTED_ARGS_LENGTH
     * EXPECTED_ARGS_LENGTH}.
     *
     * @param args
     *             the array of arguments
     *
     * @throws ProgramArgumentException
     *                                  if args has length less than
     *                                  {@link com.jamiecheung.apps.columneddataformatter.constants.CommonConstants#EXPECTED_ARGS_LENGTH
     *                                  EXPECTED_ARGS_LENGTH}
     */
    public static void validateArguments(String[] args) throws ProgramArgumentException {

        int argsLength = args.length;

        if (argsLength < EXPECTED_ARGS_LENGTH) {
            if (argsLength == ACTUAL_ARGS_LENGTH_ZERO) {
                throw new ProgramArgumentException(String.format(ERROR_MESSAGE_NOT_ENOUGH_ARGS, EMPTY_STRING,
                        EXPECTED_ARG_1 + COMMA_SPACE + EXPECTED_ARG_2 + COMMA_SPACE + EXPECTED_ARG_3));
            } else if (argsLength == ACTUAL_ARGS_LENGTH_ONE) {
                throw new ProgramArgumentException(String.format(ERROR_MESSAGE_NOT_ENOUGH_ARGS, EXPECTED_ARG_1,
                        EXPECTED_ARG_2 + COMMA_SPACE + EXPECTED_ARG_3));
            } else if (argsLength == ACTUAL_ARGS_LENGTH_TWO) {
                throw new ProgramArgumentException(String.format(ERROR_MESSAGE_NOT_ENOUGH_ARGS,
                        EXPECTED_ARG_1 + COMMA_SPACE + EXPECTED_ARG_2, EXPECTED_ARG_3));
            }
        }

    }

    /**
     * Checks the arg String corresponds to an existing path.
     * 
     * @param arg
     *            the String that may or may not represent some path
     * 
     * @return true iff arg corresponds to an existing path, otherwise false.
     */
    public static boolean isArgumentAPath(String arg) {

        Path argAsPath = Paths.get(arg);

        if (Files.exists(argAsPath)) {
            return true;
        } else {
            return false;
        }

    }

}
