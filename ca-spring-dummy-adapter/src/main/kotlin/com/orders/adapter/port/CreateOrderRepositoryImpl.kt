package com.orders.adapter.port

import com.orders.adapter.repository.OrderDummyRepository
import com.orders.domain.Order
import com.orders.domain.Owner
import com.orders.usecase.port.CreateOrderRepository
import org.springframework.stereotype.Service

@Service
class CreateOrderRepositoryImpl(private val repository: OrderDummyRepository) : CreateOrderRepository {

    override fun create(owner: Owner, order: Order): Order {
        return this.repository.save(order)
    }
}