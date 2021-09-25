package com.orders.application.api.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class ShippingView(
    @JsonProperty("address")
    val address: AddressView? = null,

    @JsonProperty("cost")
    val cost: BigDecimal? = null,

    @JsonProperty("method")
    val method: String? = null
)
