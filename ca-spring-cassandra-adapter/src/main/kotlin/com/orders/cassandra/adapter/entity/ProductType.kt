package com.orders.cassandra.adapter.entity

import org.springframework.data.cassandra.core.mapping.CassandraType
import org.springframework.data.cassandra.core.mapping.UserDefinedType
import java.math.BigDecimal
import java.math.BigInteger

@UserDefinedType("product")
data class ProductType(
    var code: String,
    var discount: BigDecimal = BigDecimal.ZERO,
    var image: String = "",
    var name: String,
    var price: BigDecimal,
    @CassandraType(type = CassandraType.Name.INT)
    var quantity: BigInteger,
    var taxes: BigDecimal = BigDecimal.ZERO
)
