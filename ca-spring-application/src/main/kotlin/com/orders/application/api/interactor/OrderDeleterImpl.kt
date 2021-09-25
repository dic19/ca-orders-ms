package com.orders.application.api.interactor

import com.orders.usecase.DeleteOrderUseCase
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import java.util.*

@Component
class OrderDeleterImpl(private val deleteOrderUseCase: DeleteOrderUseCase) : OrderDeleter {

    override fun delete(headers: HttpHeaders, uniqueID: UUID) {
        val credentials = ViewDomainAdapter.toCredentials(headers)
        this.deleteOrderUseCase.execute(credentials, uniqueID)
    }
}