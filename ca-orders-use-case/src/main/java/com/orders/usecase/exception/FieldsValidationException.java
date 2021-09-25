package com.orders.usecase.exception;

import com.orders.usecase.validation.ValidationError;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class FieldsValidationException extends AbstractUseCaseException {

    private final Set<ValidationError> errors;

    public FieldsValidationException(String message) {
        super(ErrorCodes.INVALID_FIELD_VALUE_CODE, message);
        this.errors = Collections.emptySet();
    }

    public FieldsValidationException(Set<ValidationError> errors) {
        super(ErrorCodes.INVALID_FIELD_VALUE_CODE, errors.stream()
            .map(ValidationError::getErrorMessage)
            .collect(Collectors.joining(" ")));
        this.errors = Collections.unmodifiableSet(errors);
    }

    public Set<ValidationError> getErrors() {
        return errors;
    }
}
