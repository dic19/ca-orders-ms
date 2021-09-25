package com.orders.application.api.interactor

import com.orders.application.api.model.OrderResponse
import org.springframework.http.HttpHeaders
import java.util.*

interface OrderFinder {

    fun get(headers: HttpHeaders, uniqueID: UUID): OrderResponse

    fun find(headers: HttpHeaders, referenceID: String?): OrderResponse
}