package com.authentication.domain;

import java.util.stream.Stream;

public enum CredentialsType {
    API_KEY,
    JWT,
    NOOP;

    public static CredentialsType fromString(String value) {
        return Stream.of(values())
            .filter(os -> os.name().equalsIgnoreCase(value))
            .findFirst()
            .orElse(null);
    }
}
