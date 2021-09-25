package com.orders.adapter.entity

import com.orders.domain.Country
import java.math.BigDecimal

data class ShippingEntity(
    var city: String = "",
    var cost: BigDecimal = BigDecimal.ZERO,
    var country: Country,
    var description: String = "",
    var method: String,
    var neighborhood: String = "",
    var number: String = "",
    var state: String = "",
    var street: String = "",
    var zip: String = ""
)
