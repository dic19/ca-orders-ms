package com.orders.usecase.validation;

import java.util.Set;

public interface Validator {

    Set<ValidationError> validate();

    boolean approved();

    boolean rejected();
}
