package com.orders.adapter.entity

import java.math.BigDecimal
import java.math.BigInteger
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "products")
data class ProductEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: BigInteger? = null,

    @Column(name = "code")
    var code: String,

    @Column(name = "discount")
    var discount: BigDecimal = BigDecimal.ZERO,

    @Column(name = "image")
    var image: String = "",

    @ManyToOne(fetch = FetchType.LAZY)
    var order: OrderEntity? = null,

    @Column(name = "name")
    var name: String,

    @Column(name = "price")
    var price: BigDecimal,

    @Column(name = "quantity")
    var quantity: BigInteger,

    @Column(name = "taxes")
    var taxes: BigDecimal = BigDecimal.ZERO
) {

    override fun hashCode(): Int {
        return id.hashCode() + code.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return Objects.equals(this.hashCode(), other?.hashCode())
    }

}
