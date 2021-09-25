package com.orders.domain;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Country {
    AR("AR", "ARG", "032", Currency.ARS),
    BO("BO", "BOL", "068", Currency.BOB),
    BR("BR", "BRA", "076", Currency.BRL),
    CL("CL", "CHL", "152", Currency.CLP),
    CO("CO", "COL", "170", Currency.COP),
    EC("EC", "ECU", "218", Currency.USD),
    MX("MX", "MEX", "484", Currency.MXN),
    PE("PE", "PER", "604", Currency.PEN, Currency.USD),
    PY("PY", "PRY", "600", Currency.COP),
    UY("UY", "URY", "858", Currency.UYU),
    NOOP("NOOP", "NOOP", "000", Currency.NOOP);

    private final String alpha2Code;
    private final String alpha3Code;
    private final String numericCode;
    private final Currency defaultCurrency;
    private final Set<Currency> allowedCurrencies;

    Country(String alpha2Code, String alpha3Code, String numericCode, Currency defaultCurrency, Currency... otherCurrencies) {
        this.alpha2Code = alpha2Code;
        this.alpha3Code = alpha3Code;
        this.numericCode = numericCode;
        this.defaultCurrency = defaultCurrency;
        this.allowedCurrencies = Stream.of(Stream.of(defaultCurrency), Stream.of(otherCurrencies))
            .flatMap(Function.identity())
            .collect(Collectors.toUnmodifiableSet());
    }

    public String getAlpha2Code() {
        return alpha2Code;
    }

    public String getAlpha3Code() {
        return alpha3Code;
    }

    public String getNumericCode() {
        return numericCode;
    }

    public Currency getDefaultCurrency() {
        return defaultCurrency;
    }

    public Set<Currency> getAllowedCurrencies() {
        return allowedCurrencies;
    }

    public static Country fromString(String value) {
        return Stream.of(values())
            .filter(c -> c.alpha2Code.equalsIgnoreCase(value) || c.alpha3Code.equalsIgnoreCase(value) || c.numericCode.equals(value))
            .findFirst()
            .orElse(null);
    }
}
