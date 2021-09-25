package com.orders.application.api.model

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateOrderRequest(
    @JsonProperty("products")
    val products: Set<ProductView> = emptySet(),

    @JsonProperty("shipping_required")
    val shippingRequired: Boolean = false,

    @JsonProperty("shipping")
    val shipping: ShippingView? = null
)
