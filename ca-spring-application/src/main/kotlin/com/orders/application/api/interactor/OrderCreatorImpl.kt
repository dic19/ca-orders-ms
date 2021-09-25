package com.orders.application.api.interactor

import com.orders.application.api.model.CreateOrderRequest
import com.orders.application.api.model.OrderResponse
import com.orders.usecase.CreateOrderUseCase
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component

@Component
class OrderCreatorImpl(private val createOrderUseCase: CreateOrderUseCase) : OrderCreator {

    override fun create(headers: HttpHeaders, createOrderRequest: CreateOrderRequest): OrderResponse {
        val credentials = ViewDomainAdapter.toCredentials(headers)
        val order = ViewDomainAdapter.toOrder(createOrderRequest)
        return DomainViewAdapter.toOrderResponse(this.createOrderUseCase.execute(credentials, order))
    }
}