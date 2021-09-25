package com.orders.usecase.validation;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Validators {

    private Validators() {
    }

    public static <T> Validator notNullValidator(String fieldName, T fieldValue) {
        return withPredicate(fieldName, fieldValue, Objects::nonNull);
    }

    public static Validator notBlankValidator(String fieldName, String fieldValue) {
        return withPredicate(fieldName, fieldValue, fv -> Objects.nonNull(fv) && !fv.trim().isEmpty());
    }

    public static <T> Validator notEmptyValidator(String fieldName, Collection<T> fieldValue) {
        return withPredicate(fieldName, fieldValue, fv -> Objects.nonNull(fv) && !fv.isEmpty());
    }

    public static <T> Validator inListValidator(String fieldName, T fieldValue, Collection<T> allowedValues) {
        return withPredicate(fieldName, fieldValue, allowedValues::contains);
    }

    public static <T> Validator notInListValidator(String fieldName, T fieldValue, Collection<T> forbiddenValues) {
        return withPredicate(fieldName, fieldValue, fv -> !forbiddenValues.contains(fv));
    }

    public static Validator positiveValidator(String fieldName, Number fieldValue, boolean nullable) {
        return withPredicate(fieldName, fieldValue, fv ->
            (Objects.nonNull(fv) && fv.doubleValue() > 0d) || (Objects.isNull(fv) && nullable)
        );
    }

    public static Validator zeroOrPositiveValidator(String fieldName, Number fieldValue, boolean nullable) {
        return withPredicate(fieldName, fieldValue, fv ->
            (Objects.nonNull(fv) && fv.doubleValue() >= 0d) || (Objects.isNull(fv) && nullable)
        );
    }

    public static Validator negativeValidator(String fieldName, Number fieldValue, boolean nullable) {
        return withPredicate(fieldName, fieldValue, fv ->
            (Objects.nonNull(fv) && fv.doubleValue() < 0d) || (Objects.isNull(fv) && nullable)
        );
    }

    public static Validator zeroOrNegativeValidator(String fieldName, Number fieldValue, boolean nullable) {
        return withPredicate(fieldName, fieldValue, fv ->
            (Objects.nonNull(fv) && fv.doubleValue() <= 0d) || (Objects.isNull(fv) && nullable)
        );
    }

    public static <T extends Number> Validator rangeValidator(String fieldName, Comparable<T> fieldValue, T minRangeValue, T maxRangeValue) {
        return withPredicate(fieldName, fieldValue, fv ->
            Objects.nonNull(fv) && fv.compareTo(minRangeValue) >= 0 && fv.compareTo(maxRangeValue) <= 0
        );
    }

    public static Validator regexValidator(String fieldName, String fieldValue, String regex) {
        return withPredicate(fieldName, fieldValue, fv -> Objects.nonNull(fv) && fv.matches(regex));
    }

    public static <T> Validator withPredicate(String fieldName, T fieldValue, Predicate<T> predicate) {
        return new Validator() {
            @Override
            public Set<ValidationError> validate() {
                return predicate.test(fieldValue)
                    ? Collections.emptySet()
                    : Collections.singleton(new ValidationError(fieldName, fieldValue));
            }

            @Override
            public boolean approved() {
                return predicate.test(fieldValue);
            }

            @Override
            public boolean rejected() {
                return !predicate.test(fieldValue);
            }
        };
    }

    public static Validator compose(Validator... validators) {
        return new Validator() {
            @Override
            public Set<ValidationError> validate() {
                final var errors = new LinkedHashSet<ValidationError>();
                Arrays.asList(validators).forEach(validator -> errors.addAll(validator.validate()));
                return errors;
            }

            @Override
            public boolean approved() {
                return Stream.of(validators).allMatch(Validator::approved);
            }

            @Override
            public boolean rejected() {
                return Stream.of(validators).anyMatch(Validator::rejected);
            }
        };
    }
}
