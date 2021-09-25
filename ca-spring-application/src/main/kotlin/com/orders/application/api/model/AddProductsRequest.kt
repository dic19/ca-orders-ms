package com.orders.application.api.model

import com.fasterxml.jackson.annotation.JsonProperty

data class AddProductsRequest(
    @JsonProperty("products")
    val products: Set<ProductView> = emptySet()
)
