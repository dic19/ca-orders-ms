package com.orders.usecase.validation;

public class ValidationError {

    private final String name;
    private final Object rejectedValue;

    ValidationError(String name, Object rejectedValue) {
        this.name = name;
        this.rejectedValue = rejectedValue;
    }

    public String getErrorMessage() {
        return String.format("Invalid value [%s] for field [%s].", this.rejectedValue, this.name);
    }
}
