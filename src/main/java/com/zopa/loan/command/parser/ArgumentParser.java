package com.zopa.loan.command.parser;

import com.zopa.loan.command.LoanCalculationArguments;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.lang.Long.parseLong;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Component
public class ArgumentParser {

    private static final int ARGUMENTS_COUNT = 2;
    private static final int ARGUMENT_INDEX_FILENAME = 0;
    private static final int ARGUMENT_INDEX_AMOUNT = 1;

    private static final String ERROR_INVALID_ARGUMENTS_COUNT = "Invalid arguments count. Expecting %s arguments.";
    private static final String ERROR_INVALID_ARGUMENT_AMOUNT = "Invalid argument: 'amount'. Expecting a valid integer number.";

    public LoanCalculationArguments parse(List<String> arguments) {

        if (!isValidArgumentCount(arguments)) {
            throw new IllegalArgumentException(String.format(ERROR_INVALID_ARGUMENTS_COUNT, ARGUMENTS_COUNT));
        }

        return new LoanCalculationArguments(getFilename(arguments), getAmount(arguments));
    }

    private boolean isValidArgumentCount(List<String> arguments) {
        return isNotEmpty(arguments) && arguments.size() == ARGUMENTS_COUNT;
    }

    private String getFilename(List<String> arguments) {
        return arguments.get(ARGUMENT_INDEX_FILENAME);
    }

    private long getAmount(List<String> arguments) {
        try {
            return parseLong(arguments.get(ARGUMENT_INDEX_AMOUNT));
        } catch (NumberFormatException | NullPointerException e) {
            throw new IllegalArgumentException(ERROR_INVALID_ARGUMENT_AMOUNT, e);
        }
    }
}
