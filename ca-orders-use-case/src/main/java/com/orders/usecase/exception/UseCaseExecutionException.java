package com.orders.usecase.exception;

public class UseCaseExecutionException extends AbstractUseCaseException {

    public UseCaseExecutionException(String message) {
        super(ErrorCodes.USE_CASE_EXECUTION_EXCEPTION_CODE, message);
    }

    public UseCaseExecutionException(String message, Throwable cause) {
        super(ErrorCodes.USE_CASE_EXECUTION_EXCEPTION_CODE, message, cause);
    }
}
