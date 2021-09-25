package com.orders.usecase;

import com.authentication.domain.Credentials;
import com.orders.domain.Order;
import com.orders.domain.Product;

import java.util.Set;
import java.util.UUID;

public interface AddProductsUseCase {

    Order execute(Credentials credentials, UUID uniqueID, Set<Product> products);
}
