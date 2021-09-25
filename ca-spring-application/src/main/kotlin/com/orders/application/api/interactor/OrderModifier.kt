package com.orders.application.api.interactor

import com.orders.application.api.model.AddProductsRequest
import com.orders.application.api.model.OrderResponse
import com.orders.application.api.model.UpdateOrderRequest
import org.springframework.http.HttpHeaders
import java.util.*

interface OrderModifier {

    fun update(headers: HttpHeaders, uniqueID: UUID, request: UpdateOrderRequest): OrderResponse

    fun addProducts(headers: HttpHeaders, uniqueID: UUID, request: AddProductsRequest): OrderResponse

    fun dropProduct(headers: HttpHeaders, uniqueID: UUID, productCode: String): OrderResponse
}