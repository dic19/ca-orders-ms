package com.orders.application.api.interactor

import com.orders.application.api.model.OrderResponse
import com.orders.usecase.FindOrderUseCase
import com.orders.usecase.GetOrderUseCase
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import java.util.*

@Component
class OrderFinderImpl(
    private val getOrderUseCase: GetOrderUseCase,
    private val findOrderUseCase: FindOrderUseCase
) : OrderFinder {

    override fun get(headers: HttpHeaders, uniqueID: UUID): OrderResponse {
        val credentials = ViewDomainAdapter.toCredentials(headers)
        return DomainViewAdapter.toOrderResponse(this.getOrderUseCase.execute(credentials, uniqueID))
    }

    override fun find(headers: HttpHeaders, referenceID: String?): OrderResponse {
        val credentials = ViewDomainAdapter.toCredentials(headers)
        return DomainViewAdapter.toOrderResponse(this.findOrderUseCase.execute(credentials, referenceID))
    }
}