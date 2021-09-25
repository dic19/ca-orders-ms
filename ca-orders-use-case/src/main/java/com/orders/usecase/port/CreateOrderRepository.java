package com.orders.usecase.port;

import com.orders.domain.Order;
import com.orders.domain.Owner;

public interface CreateOrderRepository {

    Order create(Owner owner, Order order);
}
