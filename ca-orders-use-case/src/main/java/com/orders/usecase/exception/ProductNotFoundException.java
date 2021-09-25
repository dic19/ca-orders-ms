package com.orders.usecase.exception;

import java.util.UUID;

public class ProductNotFoundException extends AbstractUseCaseException {

    private final UUID orderID;
    private final String productCode;

    public ProductNotFoundException(UUID orderID, String productCode) {
        super(ErrorCodes.PRODUCT_NOT_FOUND_CODE
            , String.format("Product with code [%s] not found in order [%s]!", productCode, orderID));
        this.orderID = orderID;
        this.productCode = productCode;
    }

    public UUID getOrderID() {
        return orderID;
    }

    public String getProductCode() {
        return productCode;
    }
}
