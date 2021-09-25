package com.orders.application.api.model

data class ApiErrorResponse(val error: ErrorView) {
    data class ErrorView(
        val code: String,
        val message: String,
        val cause: String
    )
}