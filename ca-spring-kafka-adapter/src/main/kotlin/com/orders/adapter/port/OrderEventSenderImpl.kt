package com.orders.adapter.port

import com.fasterxml.jackson.databind.ObjectMapper
import com.orders.adapter.SpringKafkaConfiguration
import com.orders.adapter.model.OrderEventMessage
import com.orders.domain.Order
import com.orders.domain.OrderEventType
import com.orders.usecase.port.OrderEventSender
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class OrderEventSenderImpl(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val mapper: ObjectMapper
) : OrderEventSender {

    private val topicsMap = mapOf(
        OrderEventType.CREATED to SpringKafkaConfiguration.Topics.CREATED_ORDER_TOPIC_NAME,
        OrderEventType.DELETED to SpringKafkaConfiguration.Topics.DELETED_ORDER_TOPIC_NAME,
        OrderEventType.UPDATED to SpringKafkaConfiguration.Topics.UPDATED_ORDER_TOPIC_NAME
    )

    override fun send(eventType: OrderEventType, order: Order) {
        val topicName = this.topicsMap[eventType].orEmpty()
        val message = this.mapper.writeValueAsString(OrderEventMessage(eventType, order))
        kafkaTemplate.send(topicName, message)
    }
}