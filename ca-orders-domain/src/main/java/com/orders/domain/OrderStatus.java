package com.orders.domain;

import java.util.stream.Stream;

public enum OrderStatus {
    CANCELLED,
    CREATED,
    IN_PROGRESS,
    PAYMENT_APPROVED,
    PAYMENT_REJECTED;

    public static OrderStatus fromString(String value) {
        return Stream.of(values())
            .filter(os -> os.name().equalsIgnoreCase(value))
            .findFirst()
            .orElse(null);
    }
}
