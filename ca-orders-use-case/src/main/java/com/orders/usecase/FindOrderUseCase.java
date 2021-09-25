package com.orders.usecase;

import com.authentication.domain.Credentials;
import com.orders.domain.Order;

public interface FindOrderUseCase {

    Order execute(Credentials credentials, String referenceID);
}
