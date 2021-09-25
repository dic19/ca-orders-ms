package com.orders.domain;

import java.util.stream.Stream;

public enum OwnerType {
    NOOP,
    STORE,
    USER;

    public static OwnerType fromString(String value) {
        return Stream.of(values())
            .filter(os -> os.name().equalsIgnoreCase(value))
            .findFirst()
            .orElse(null);
    }
}
