package com.zopa.loan.command.validator;

import com.zopa.loan.command.LoanCalculationArguments;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class LoanCalculationArgumentsValidatorTest {

    private LoanCalculationArgumentsValidator validator;

    @Before
    public void setUp() {
        validator = new LoanCalculationArgumentsValidator();
    }

    @Test
    public void getValidationError_whenAmountIsBelowMinimum_shouldReturnError() {
        Optional<String> error = validator.getValidationError(argumentsWithAmount(900));

        assertTrue(error.isPresent());
        assertThat(error.get(), equalTo("Argument 'amount' should be in range: 1000...15000"));
    }

    @Test
    public void getValidationError_whenAmountIsAboveMaximum_shouldReturnError() {
        Optional<String> error = validator.getValidationError(argumentsWithAmount(15_100));

        assertTrue(error.isPresent());
        assertThat(error.get(), equalTo("Argument 'amount' should be in range: 1000...15000"));
    }

    @Test
    public void getValidationError_whenAmountIsEqualToMinimum_shouldNotReturnError() {
        Optional<String> error = validator.getValidationError(argumentsWithAmount(1_000));
        assertFalse(error.isPresent());
    }

    @Test
    public void getValidationError_whenAmountIsEqualToMaximum_shouldNotReturnError() {
        Optional<String> error = validator.getValidationError(argumentsWithAmount(15_000));
        assertFalse(error.isPresent());
    }

    @Test
    public void getValidationError_whenAmountIsNotDividableBy100_shouldReturnError() {
        Optional<String> error = validator.getValidationError(argumentsWithAmount(2_050));

        assertTrue(error.isPresent());
        assertThat(error.get(), equalTo("Argument 'amount' should be a number dividable by 100"));
    }

    @Test
    public void getValidationError_whenAmountWithinValidRangeAndDividesBy100_shouldNotReturnError() {
        Optional<String> error = validator.getValidationError(argumentsWithAmount(2_100));
        assertFalse(error.isPresent());
    }

    private LoanCalculationArguments argumentsWithAmount(long amount) {
        return new LoanCalculationArguments("test-filename.csv", amount);
    }
}