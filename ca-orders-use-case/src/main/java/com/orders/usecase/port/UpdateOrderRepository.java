package com.orders.usecase.port;

import com.orders.domain.Order;
import com.orders.domain.Owner;

import java.util.UUID;

public interface UpdateOrderRepository {

    Order update(Owner owner, UUID uniqueID, Order order);
}
