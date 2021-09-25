package com.orders.adapter.port

import com.orders.domain.Order
import com.orders.domain.OrderEventType
import com.orders.usecase.port.OrderEventSender
import org.springframework.stereotype.Component

@Component
class OrderEventSenderImpl : OrderEventSender {

    override fun send(eventType: OrderEventType, order: Order) {
        // This implementation is dummy does nothing
    }
}