package com.orders.usecase.port;

import com.orders.domain.Order;
import com.orders.domain.Owner;

import java.util.UUID;

public interface DeleteOrderRepository {

    Order delete(Owner owner, UUID uniqueID);
}
