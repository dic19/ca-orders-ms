package com.orders.application.api.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.orders.domain.Country

data class AddressView(
    @JsonProperty("city")
    val city: String? = null,

    @JsonProperty("country")
    val country: Country? = null,

    @JsonProperty("description")
    val description: String? = null,

    @JsonProperty("neighborhood")
    val neighborhood: String? = null,

    @JsonProperty("number")
    val number: String? = null,

    @JsonProperty("state")
    val state: String? = null,

    @JsonProperty("street")
    val street: String? = null,

    @JsonProperty("zip")
    val zip: String? = null
)