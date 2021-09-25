package com.orders.usecase.port;

import com.orders.domain.Order;
import com.orders.domain.Owner;

public interface FindOrderRepository {

    Order findByReferenceID(Owner owner, String referenceID);
}
