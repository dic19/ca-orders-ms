package com.orders.usecase.exception;

public abstract class AbstractUseCaseException extends RuntimeException {

    private final String errorCode;

    protected AbstractUseCaseException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    protected AbstractUseCaseException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
