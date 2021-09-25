package com.orders.usecase;

import com.authentication.domain.Credentials;
import com.orders.domain.Order;

public interface CreateOrderUseCase {

    Order execute(Credentials credentials, Order order);
}
