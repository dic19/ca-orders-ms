package com.orders.adapter.port

import com.orders.adapter.repository.OrderDummyRepository
import com.orders.domain.Order
import com.orders.domain.Owner
import com.orders.usecase.exception.OrderNotFoundException
import com.orders.usecase.port.UpdateOrderRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@Service
class UpdateOrderRepositoryImpl(private val repository: OrderDummyRepository) : UpdateOrderRepository {

    override fun update(owner: Owner, uniqueID: UUID, order: Order): Order {
        val oldOrder = this.repository.get(owner.type, owner.uniqueID, uniqueID).orElseThrow {
            OrderNotFoundException(uniqueID.toString())
        }

        val newOrder = Order.builder(order)
            .wasCreatedAt(oldOrder.createdAt)
            .wasUpdatedAt(LocalDateTime.now().atOffset(ZoneOffset.UTC))
            .build()

        return this.repository.update(newOrder)
    }
}