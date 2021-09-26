package com.orders.kafka.adapter

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.kafka.annotation.EnableKafka

@Configuration
@EnableKafka
@PropertySource("classpath:ca-orders-kafka-adapter.properties")
class SpringKafkaConfiguration {

    object Topics {
        const val CREATED_ORDER_TOPIC_NAME = "kafka_created_order_topic"
        const val DELETED_ORDER_TOPIC_NAME = "kafka_deleted_order_topic"
        const val UPDATED_ORDER_TOPIC_NAME = "kafka_updated_order_topic"
    }
}