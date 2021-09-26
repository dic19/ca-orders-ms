package com.orders.jpa.adapter.entity

import com.orders.domain.Country
import java.math.BigDecimal
import java.math.BigInteger
import javax.persistence.*

@Entity
@Table(name = "shipping")
data class ShippingEntity(
    @Id
    val id: BigInteger? = null,

    @Column(name = "city")
    var city: String = "",

    @Column(name = "cost")
    var cost: BigDecimal = BigDecimal.ZERO,

    @Enumerated(EnumType.STRING)
    @Column(name = "country")
    var country: Country,

    @Column(name = "description")
    var description: String = "",

    @Column(name = "method")
    var method: String,

    @Column(name = "neighborhood")
    var neighborhood: String = "",

    @Column(name = "number")
    var number: String = "",

    @MapsId
    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    var order: OrderEntity? = null,

    @Column(name = "state")
    var state: String = "",

    @Column(name = "street")
    var street: String = "",

    @Column(name = "zip")
    var zip: String = ""
)
