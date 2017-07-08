package com.zopa.loan.command.resolver;

import com.zopa.loan.command.LoanCalculationArguments;
import com.zopa.loan.command.parser.ArgumentParser;
import com.zopa.loan.command.validator.ArgumentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Arrays.asList;

@Component
public class ArgumentResolver {

    private final ArgumentParser parser;
    private final ArgumentValidator validator;

    @Autowired
    public ArgumentResolver(ArgumentParser parser, ArgumentValidator validator) {
        this.parser = parser;
        this.validator = validator;
    }

    public LoanCalculationArguments resolve(String[] arguments) {
        LoanCalculationArguments parsedArguments = parser.parse(asList(arguments));

        Optional<String> validationError = validator.getValidationError(parsedArguments);

        validationError.ifPresent(errorMessage -> {
            throw new IllegalArgumentException(errorMessage);
        });

        return parsedArguments;
    }
}
