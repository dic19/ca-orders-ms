package com.orders.dummy.adapter.port

import com.orders.dummy.adapter.repository.OrderDummyRepository
import com.orders.domain.Order
import com.orders.domain.Owner
import com.orders.usecase.exception.OrderNotFoundException
import com.orders.usecase.port.FindOrderRepository

class FindOrderRepositoryImpl(private val repository: OrderDummyRepository) : FindOrderRepository {

    override fun findByReferenceID(owner: Owner, referenceID: String): Order {
        return this.repository.find(owner.type, owner.uniqueID, referenceID).orElseThrow {
            OrderNotFoundException(referenceID)
        }
    }
}