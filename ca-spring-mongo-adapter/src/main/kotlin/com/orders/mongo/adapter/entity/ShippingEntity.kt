package com.orders.mongo.adapter.entity

import com.orders.domain.Country
import org.springframework.data.mongodb.core.mapping.Field
import java.math.BigDecimal

data class ShippingEntity(
    @Field("city")
    var city: String = "",

    @Field("cost")
    var cost: BigDecimal = BigDecimal.ZERO,

    @Field("country")
    var country: Country,

    @Field("description")
    var description: String = "",

    @Field("method")
    var method: String,

    @Field("neighborhood")
    var neighborhood: String = "",

    @Field("number")
    var number: String = "",

    @Field("state")
    var state: String = "",

    @Field("street")
    var street: String = "",

    @Field("zip")
    var zip: String = ""
)
