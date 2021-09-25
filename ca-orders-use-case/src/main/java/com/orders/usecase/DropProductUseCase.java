package com.orders.usecase;

import com.authentication.domain.Credentials;
import com.orders.domain.Order;

import java.util.UUID;

public interface DropProductUseCase {

    Order execute(Credentials credentials, UUID uniqueID, String productCode);
}
