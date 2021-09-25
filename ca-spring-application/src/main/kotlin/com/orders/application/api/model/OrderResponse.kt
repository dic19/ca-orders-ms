package com.orders.application.api.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.orders.domain.Country
import com.orders.domain.Currency
import com.orders.domain.OrderStatus
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.*

data class OrderResponse(
    @JsonProperty("country")
    val country: Country,

    @JsonProperty("created_at")
    val createdAt: OffsetDateTime,

    @JsonProperty("currency")
    val currency: Currency,

    @JsonProperty("owner")
    val owner: OwnerView,

    @JsonProperty("products")
    val products: Set<ProductView>,

    @JsonProperty("reference_id")
    val referenceID: String,

    @JsonProperty("shipping_required")
    val shippingRequired: Boolean,

    @JsonProperty("shipping")
    val shipping: ShippingView? = null,

    @JsonProperty("status")
    val status: OrderStatus,

    @JsonProperty("subtotal")
    val subtotal: BigDecimal,

    @JsonProperty("total")
    val total: BigDecimal,

    @JsonProperty("unique_id")
    val uniqueID: UUID,

    @JsonProperty("updated_at")
    val updatedAt: OffsetDateTime
)
