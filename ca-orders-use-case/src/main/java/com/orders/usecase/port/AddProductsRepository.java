package com.orders.usecase.port;

import com.orders.domain.Order;
import com.orders.domain.Owner;
import com.orders.domain.Product;

import java.util.Set;
import java.util.UUID;

public interface AddProductsRepository {

    Order addProducts(Owner owner, UUID uniqueID, Set<Product> products);
}
