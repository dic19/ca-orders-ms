package com.orders.application.api.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.orders.domain.Country
import com.orders.domain.OwnerType
import java.util.*

data class OwnerView(
    @JsonProperty("country")
    val country: Country,

    @JsonProperty("type")
    val type: OwnerType,

    @JsonProperty("unique_id")
    val uniqueID: UUID
)
