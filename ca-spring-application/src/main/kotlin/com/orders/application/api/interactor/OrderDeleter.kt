package com.orders.application.api.interactor

import org.springframework.http.HttpHeaders
import java.util.*

interface OrderDeleter {

    fun delete(headers: HttpHeaders, uniqueID: UUID)
}