package com.orders.usecase.exception;

public class OrderNotFoundException extends AbstractUseCaseException {

    private final String identifier;

    public OrderNotFoundException(String identifier) {
        super(ErrorCodes.ORDER_NOT_FOUND_CODE
            , String.format("Order with ID or reference [%s] not found!", identifier));
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }
}
