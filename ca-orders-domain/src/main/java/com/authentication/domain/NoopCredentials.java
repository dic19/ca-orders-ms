package com.authentication.domain;

public class NoopCredentials extends Credentials {

    NoopCredentials() {
        super(CredentialsType.NOOP, CredentialsType.NOOP.name());
    }
}
