package com.orders.domain;

import java.util.stream.Stream;

public enum OrderEventType {
    CREATED,
    DELETED,
    UPDATED;

    public static OrderEventType fromString(String value) {
        return Stream.of(values())
            .filter(os -> os.name().equalsIgnoreCase(value))
            .findFirst()
            .orElse(null);
    }
}
