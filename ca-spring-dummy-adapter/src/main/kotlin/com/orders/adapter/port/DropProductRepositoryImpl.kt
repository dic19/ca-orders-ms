package com.orders.adapter.port

import com.orders.adapter.repository.OrderDummyRepository
import com.orders.domain.Order
import com.orders.domain.Owner
import com.orders.domain.Product
import com.orders.usecase.exception.OrderNotFoundException
import com.orders.usecase.exception.ProductNotFoundException
import com.orders.usecase.port.DropProductRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@Service
class DropProductRepositoryImpl(private val repository: OrderDummyRepository) : DropProductRepository {

    override fun dropProduct(owner: Owner, uniqueID: UUID, productCode: String): Order {
        val order = this.repository.get(owner.type, owner.uniqueID, uniqueID).orElseThrow {
            OrderNotFoundException(uniqueID.toString())
        }

        val products = mutableSetOf<Product>()
        products.addAll(order.products)

        if (products.removeIf { it.code == productCode }.not()) {
            throw ProductNotFoundException(uniqueID, productCode)
        }

        val newOrder = Order.builder(order)
            .wasCreatedAt(order.createdAt)
            .wasUpdatedAt(LocalDateTime.now().atOffset(ZoneOffset.UTC))
            .withProducts(products)
            .build()

        return this.repository.update(newOrder)
    }
}