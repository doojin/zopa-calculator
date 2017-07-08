package com.zopa.loan.command.validator;

import java.util.Optional;

public interface Validator<T> {

    Optional<String> getValidationError(T object);
}
