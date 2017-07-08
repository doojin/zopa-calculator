package com.zopa.loan.command.validator;

import com.zopa.loan.command.LoanCalculationArguments;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ArgumentValidator implements Validator<LoanCalculationArguments> {

    private static final int AMOUNT_MIN = 1_000;
    private static final int AMOUNT_MAX = 15_000;

    private static final String ERROR_MESSAGE_AMOUNT_OUT_OF_RANGE =
            String.format("Argument 'amount' should be in range: %s...%s", AMOUNT_MIN, AMOUNT_MAX);

    private static final String ERROR_MESSAGE_SHOULD_DIVIDE_BY_100 =
            "Argument 'amount' should be a number dividable by 100";

    @Override
    public Optional<String> getValidationError(LoanCalculationArguments arguments) {

        if (!isValidRange(arguments.getAmount())) {
            return Optional.of(ERROR_MESSAGE_AMOUNT_OUT_OF_RANGE);
        }

        if (!isDividableBy100(arguments.getAmount())) {
            return Optional.of(ERROR_MESSAGE_SHOULD_DIVIDE_BY_100);
        }

        return Optional.empty();
    }

    private boolean isValidRange(long amount) {
        return amount >= AMOUNT_MIN && amount <= AMOUNT_MAX;
    }

    private boolean isDividableBy100(long amount) {
        return amount % 100 == 0;
    }
}
