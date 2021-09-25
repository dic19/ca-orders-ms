package com.orders.domain;

import java.util.stream.Stream;

public enum Currency {
    ARS("ARS", "032"),
    BOB("BOB", "068"),
    BRL("BRL", "986"),
    CLP("CLP", "152"),
    COP("COP", "170"),
    MXN("MXN", "484"),
    PEN("PEN", "604"),
    PYG("PYG", "600"),
    USD("USD", "840"),
    UYU("UYU", "858"),
    NOOP("NOOP", "000");

    private final String alphaCode;
    private final String numericCode;

    Currency(String alphaCode, String numericCode) {
        this.alphaCode = alphaCode;
        this.numericCode = numericCode;
    }

    public String getAlphaCode() {
        return this.alphaCode;
    }

    public String getNumericCode() {
        return this.numericCode;
    }

    public static Currency fromString(String value) {
        return Stream.of(values())
            .filter(c -> c.alphaCode.equalsIgnoreCase(value) || c.numericCode.equals(value))
            .findFirst()
            .orElse(null);
    }
}
