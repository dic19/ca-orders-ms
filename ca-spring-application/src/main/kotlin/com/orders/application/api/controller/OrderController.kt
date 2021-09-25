package com.orders.application.api.controller

import com.orders.application.api.Route
import com.orders.application.api.interactor.OrderCreator
import com.orders.application.api.interactor.OrderDeleter
import com.orders.application.api.interactor.OrderFinder
import com.orders.application.api.interactor.OrderModifier
import com.orders.application.api.model.AddProductsRequest
import com.orders.application.api.model.CreateOrderRequest
import com.orders.application.api.model.OrderResponse
import com.orders.application.api.model.UpdateOrderRequest
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class OrderController(
    private val orderCreator: OrderCreator,
    private val orderDeleter: OrderDeleter,
    private val orderFinder: OrderFinder,
    private val orderModifier: OrderModifier
) {

    @GetMapping(path = [Route.ORDERS_BY_ID_PATH], produces = [APPLICATION_JSON_VALUE])
    fun get(
        @RequestHeader headers: HttpHeaders,
        @PathVariable("uniqueID") uniqueID: UUID
    ): OrderResponse {
        return this.orderFinder.get(headers, uniqueID)
    }

    @GetMapping(path = [Route.ORDERS_PATH], produces = [APPLICATION_JSON_VALUE])
    fun find(
        @RequestHeader headers: HttpHeaders,
        @RequestParam(required = false, name = "reference_id") referenceID: String?
    ): OrderResponse {
        return this.orderFinder.find(headers, referenceID)
    }

    @PostMapping(path = [Route.ORDERS_PATH], consumes = [APPLICATION_JSON_VALUE], produces = [APPLICATION_JSON_VALUE])
    fun create(
        @RequestHeader headers: HttpHeaders,
        @RequestBody createOrderRequest: CreateOrderRequest
    ): OrderResponse {
        return this.orderCreator.create(headers, createOrderRequest)
    }

    @PutMapping(
        path = [Route.ORDERS_BY_ID_PATH],
        consumes = [APPLICATION_JSON_VALUE],
        produces = [APPLICATION_JSON_VALUE]
    )
    fun update(
        @RequestHeader headers: HttpHeaders,
        @PathVariable("uniqueID") uniqueID: UUID,
        @RequestBody updateOrderRequest: UpdateOrderRequest
    ): OrderResponse {
        return this.orderModifier.update(headers, uniqueID, updateOrderRequest)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = [Route.ORDERS_BY_ID_PATH])
    fun delete(
        @RequestHeader headers: HttpHeaders,
        @PathVariable("uniqueID") uniqueID: UUID
    ) {
        this.orderDeleter.delete(headers, uniqueID)
    }

    @PostMapping(path = [Route.PRODUCTS_PATH], consumes = [APPLICATION_JSON_VALUE], produces = [APPLICATION_JSON_VALUE])
    fun addProducts(
        @RequestHeader headers: HttpHeaders,
        @PathVariable("uniqueID") uniqueID: UUID,
        @RequestBody addProductsRequest: AddProductsRequest
    ): OrderResponse {
        return this.orderModifier.addProducts(headers, uniqueID, addProductsRequest)
    }

    @DeleteMapping(path = [Route.PRODUCTS_BY_CODE_PATH], produces = [APPLICATION_JSON_VALUE])
    fun dropProduct(
        @RequestHeader headers: HttpHeaders,
        @PathVariable("uniqueID") uniqueID: UUID,
        @PathVariable("productCode") productCode: String
    ): OrderResponse {
        return this.orderModifier.dropProduct(headers, uniqueID, productCode)
    }
}