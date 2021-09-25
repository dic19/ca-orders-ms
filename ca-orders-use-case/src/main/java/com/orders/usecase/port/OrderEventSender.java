package com.orders.usecase.port;

import com.orders.domain.Order;
import com.orders.domain.OrderEventType;

public interface OrderEventSender {

    void send(OrderEventType eventType, Order order);
}
