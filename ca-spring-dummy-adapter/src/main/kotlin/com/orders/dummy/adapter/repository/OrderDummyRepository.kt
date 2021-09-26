package com.orders.dummy.adapter.repository

import com.orders.domain.Order
import com.orders.domain.OwnerType
import java.util.*
import java.util.concurrent.CopyOnWriteArraySet

class OrderDummyRepository {

    private val ordersSet: CopyOnWriteArraySet<Order> = CopyOnWriteArraySet()

    fun get(ownerType: OwnerType, ownerID: UUID, uniqueID: UUID): Optional<Order> {
        return this.ordersSet.stream().filter { order ->
            order.owner.type == ownerType && order.owner.uniqueID == ownerID && order.uniqueID == uniqueID
        }.findFirst()
    }

    fun find(ownerType: OwnerType, ownerID: UUID, referenceID: String): Optional<Order> {
        return this.ordersSet.stream().filter { order ->
            order.owner.type == ownerType && order.owner.uniqueID == ownerID && order.referenceID == referenceID
        }.findFirst()
    }

    fun save(order: Order): Order {
        this.ordersSet.add(order)
        return order
    }

    fun delete(order: Order): Boolean {
        return this.ordersSet.removeIf {
            it.owner.type == order.owner.type && it.owner.uniqueID == order.owner.uniqueID && it.uniqueID == order.uniqueID
        }
    }

    fun update(order: Order): Order {
        this.ordersSet.removeIf {
            it.owner.type == order.owner.type && it.owner.uniqueID == order.owner.uniqueID && it.uniqueID == order.uniqueID
        }
        this.ordersSet.add(order)
        return order
    }
}