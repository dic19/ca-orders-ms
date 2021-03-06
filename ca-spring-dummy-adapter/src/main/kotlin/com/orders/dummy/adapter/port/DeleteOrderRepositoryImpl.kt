package com.orders.dummy.adapter.port

import com.orders.dummy.adapter.repository.OrderDummyRepository
import com.orders.domain.Order
import com.orders.domain.Owner
import com.orders.usecase.exception.OrderNotFoundException
import com.orders.usecase.port.DeleteOrderRepository
import java.util.*

class DeleteOrderRepositoryImpl(private val repository: OrderDummyRepository) : DeleteOrderRepository {

    override fun delete(owner: Owner, uniqueID: UUID): Order {
        val order = this.repository.get(owner.type, owner.uniqueID, uniqueID).orElseThrow {
            OrderNotFoundException(uniqueID.toString())
        }
        this.repository.delete(order)
        return order
    }
}