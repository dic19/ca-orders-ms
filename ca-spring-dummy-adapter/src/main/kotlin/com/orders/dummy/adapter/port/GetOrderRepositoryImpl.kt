package com.orders.dummy.adapter.port

import com.orders.dummy.adapter.repository.OrderDummyRepository
import com.orders.domain.Order
import com.orders.domain.Owner
import com.orders.usecase.exception.OrderNotFoundException
import com.orders.usecase.port.GetOrderRepository
import java.util.*

class GetOrderRepositoryImpl(private val repository: OrderDummyRepository) : GetOrderRepository {

    override fun get(owner: Owner, uniqueID: UUID): Order {
        return this.repository.get(owner.type, owner.uniqueID, uniqueID).orElseThrow {
            OrderNotFoundException(uniqueID.toString())
        }
    }
}