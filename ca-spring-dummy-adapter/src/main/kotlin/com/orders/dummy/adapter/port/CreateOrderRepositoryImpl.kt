package com.orders.dummy.adapter.port

import com.orders.dummy.adapter.repository.OrderDummyRepository
import com.orders.domain.Order
import com.orders.domain.Owner
import com.orders.usecase.port.CreateOrderRepository

class CreateOrderRepositoryImpl(private val repository: OrderDummyRepository) : CreateOrderRepository {

    override fun create(owner: Owner, order: Order): Order {
        return this.repository.save(order)
    }
}