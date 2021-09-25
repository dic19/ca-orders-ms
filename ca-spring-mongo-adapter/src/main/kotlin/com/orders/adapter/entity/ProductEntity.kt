package com.orders.adapter.entity

import org.springframework.data.mongodb.core.mapping.Field
import java.math.BigDecimal
import java.math.BigInteger

data class ProductEntity(
    @Field("code")
    var code: String,

    @Field("discount")
    var discount: BigDecimal = BigDecimal.ZERO,

    @Field("image")
    var image: String = "",

    @Field("name")
    var name: String,

    @Field("price")
    var price: BigDecimal,

    @Field("quantity")
    var quantity: BigInteger,

    @Field("taxes")
    var taxes: BigDecimal = BigDecimal.ZERO
)
