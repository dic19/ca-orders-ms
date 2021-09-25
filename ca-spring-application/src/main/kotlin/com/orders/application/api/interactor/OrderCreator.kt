package com.orders.application.api.interactor

import com.orders.application.api.model.CreateOrderRequest
import com.orders.application.api.model.OrderResponse
import org.springframework.http.HttpHeaders

interface OrderCreator {

    fun create(headers: HttpHeaders, createOrderRequest: CreateOrderRequest): OrderResponse
}