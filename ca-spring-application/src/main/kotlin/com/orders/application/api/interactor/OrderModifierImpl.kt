package com.orders.application.api.interactor

import com.orders.application.api.model.AddProductsRequest
import com.orders.application.api.model.OrderResponse
import com.orders.application.api.model.UpdateOrderRequest
import com.orders.usecase.AddProductsUseCase
import com.orders.usecase.DropProductUseCase
import com.orders.usecase.UpdateOrderUseCase
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import java.util.*
import java.util.stream.Collectors

@Component
class OrderModifierImpl(
    private val addProductsUseCase: AddProductsUseCase,
    private val dropProductUseCase: DropProductUseCase,
    private val updateOrderUseCase: UpdateOrderUseCase
) : OrderModifier {


    override fun update(headers: HttpHeaders, uniqueID: UUID, request: UpdateOrderRequest): OrderResponse {
        val credentials = ViewDomainAdapter.toCredentials(headers)
        val order = ViewDomainAdapter.toOrder(request)
        return DomainViewAdapter.toOrderResponse(this.updateOrderUseCase.execute(credentials, uniqueID, order))
    }

    override fun addProducts(headers: HttpHeaders, uniqueID: UUID, request: AddProductsRequest): OrderResponse {
        val credentials = ViewDomainAdapter.toCredentials(headers)
        val products = request.products.stream()
            .map(ViewDomainAdapter::toProduct)
            .collect(Collectors.toSet())
        return DomainViewAdapter.toOrderResponse(this.addProductsUseCase.execute(credentials, uniqueID, products))
    }

    override fun dropProduct(headers: HttpHeaders, uniqueID: UUID, productCode: String): OrderResponse {
        val credentials = ViewDomainAdapter.toCredentials(headers)
        return DomainViewAdapter.toOrderResponse(this.dropProductUseCase.execute(credentials, uniqueID, productCode))
    }
}