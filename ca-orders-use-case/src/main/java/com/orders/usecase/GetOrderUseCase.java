package com.orders.usecase;

import com.authentication.domain.Credentials;
import com.orders.domain.Order;

import java.util.UUID;

public interface GetOrderUseCase {

    Order execute(Credentials credentials, UUID uniqueID);
}
