package com.orders.adapter.port

import com.orders.adapter.repository.OrderDummyRepository
import com.orders.domain.Order
import com.orders.domain.Owner
import com.orders.domain.Product
import com.orders.usecase.exception.OrderNotFoundException
import com.orders.usecase.port.AddProductsRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@Service
class AddProductsRepositoryImpl(private val repository: OrderDummyRepository) : AddProductsRepository {

    override fun addProducts(owner: Owner, uniqueID: UUID, products: MutableSet<Product>): Order {
        val order = this.repository.get(owner.type, owner.uniqueID, uniqueID).orElseThrow {
            OrderNotFoundException(uniqueID.toString())
        }

        products.addAll(order.products)

        val newOrder = Order.builder(order)
            .wasCreatedAt(order.createdAt)
            .wasUpdatedAt(LocalDateTime.now().atOffset(ZoneOffset.UTC))
            .withProducts(products)
            .build()

        return this.repository.update(newOrder)
    }
}