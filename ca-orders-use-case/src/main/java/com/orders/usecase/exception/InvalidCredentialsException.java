package com.orders.usecase.exception;

import com.authentication.domain.Credentials;

public class InvalidCredentialsException extends AbstractUseCaseException {

    private final Credentials credentials;

    public InvalidCredentialsException(Credentials credentials) {
        super(ErrorCodes.INVALID_CREDENTIALS_CODE, "Unauthorized");
        this.credentials = credentials;
    }

    public Credentials getCredentials() {
        return credentials;
    }
}
