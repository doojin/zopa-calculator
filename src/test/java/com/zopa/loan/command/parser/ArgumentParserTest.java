package com.zopa.loan.command.parser;

import com.zopa.loan.command.LoanCalculationArguments;
import org.junit.Before;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.fail;

public class ArgumentParserTest {

    private static final String FAILURE_MESSAGE_EXCEPTION_EXPECTED = "Exception was expected";

    private ArgumentParser parser;

    @Before
    public void setUp() {
        parser = new ArgumentParser();
    }

    @Test
    public void parse_whenInvalidArgumentCount_shouldThrowException() {
        try {
            parser.parse(asList("first", "second", "third"));
            fail(FAILURE_MESSAGE_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), equalTo("Invalid arguments count. Expecting 2 arguments."));
        }
    }

    @Test
    public void parse_whenArgumentsAreNull_shouldThrowException() {
        try {
            parser.parse(null);
            fail(FAILURE_MESSAGE_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), equalTo("Invalid arguments count. Expecting 2 arguments."));
        }
    }

    @Test
    public void parse_whenSecondArgumentIsNotAValidIntegerNumber_shouldThrowException() {
        try {
            parser.parse(asList("first", "second"));
            fail(FAILURE_MESSAGE_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), equalTo("Invalid argument: 'amount'. Expecting a valid integer number."));
        }
    }

    @Test
    public void parse_whenValidArguments_shouldParseThem() {
        LoanCalculationArguments arguments = parser.parse(asList("test-file.csv", "1234"));

        assertThat(arguments.getFilename(), equalTo("test-file.csv"));
        assertThat(arguments.getAmount(), equalTo(1234L));
    }
}