package com.orders.kafka.adapter.model

import com.orders.domain.Order
import com.orders.domain.OrderEventType

data class OrderEventMessage(
    val eventType: OrderEventType,
    val order: Order
)