package com.orders.usecase.port;

import com.orders.domain.Order;
import com.orders.domain.Owner;

import java.util.Optional;
import java.util.UUID;

public interface GetOrderRepository {

    Order get(Owner owner, UUID uniqueID);
}
