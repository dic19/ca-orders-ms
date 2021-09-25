package com.orders.application.api.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.math.BigInteger

data class ProductView(
    @JsonProperty("code")
    val code: String? = null,

    @JsonProperty("discount")
    val discount: BigDecimal? = null,

    @JsonProperty("image")
    val image: String? = null,

    @JsonProperty("name")
    val name: String? = null,

    @JsonProperty("price")
    val price: BigDecimal? = null,

    @JsonProperty("quantity")
    val quantity: BigInteger? = null,

    @JsonProperty("taxes")
    val taxes: BigDecimal? = null
)
