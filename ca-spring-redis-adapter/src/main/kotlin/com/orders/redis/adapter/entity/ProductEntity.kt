package com.orders.redis.adapter.entity

import java.math.BigDecimal
import java.math.BigInteger

data class ProductEntity(
    var code: String,
    var discount: BigDecimal = BigDecimal.ZERO,
    var image: String = "",
    var name: String,
    var price: BigDecimal,
    var quantity: BigInteger,
    var taxes: BigDecimal = BigDecimal.ZERO
)
