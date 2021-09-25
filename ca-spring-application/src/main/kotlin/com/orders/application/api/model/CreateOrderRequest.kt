package com.orders.application.api.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.orders.domain.Currency

data class CreateOrderRequest(
    @JsonProperty("currency")
    val currency: Currency? = null,

    @JsonProperty("products")
    val products: Set<ProductView> = emptySet(),

    @JsonProperty("reference_id")
    val referenceID: String? = null,

    @JsonProperty("shipping_required")
    val shippingRequired: Boolean = false,

    @JsonProperty("shipping")
    val shipping: ShippingView? = null
)
