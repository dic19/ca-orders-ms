package com.orders.adapter.entity

import com.orders.domain.Country
import com.orders.domain.Currency
import com.orders.domain.OrderStatus
import com.orders.domain.OwnerType
import java.math.BigInteger
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "orders")
data class OrderEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: BigInteger? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "country")
    val country: Country,

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    val currency: Currency,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    @Column(name = "owner_type")
    val ownerType: OwnerType,

    @Column(name = "owner_id")
    val ownerID: UUID,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
    val products: MutableSet<ProductEntity> = mutableSetOf(),

    @Column(name = "reference_id")
    val referenceID: String,

    @Column(name = "shipping_required")
    var shippingRequired: Boolean = false,

    @OneToOne(mappedBy = "order", cascade = [CascadeType.ALL], optional = true, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    var shipping: ShippingEntity? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    val status: OrderStatus = OrderStatus.CREATED,

    @Column(name = "unique_id")
    val uniqueID: UUID,

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {

    override fun hashCode(): Int {
        return uniqueID.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return Objects.equals(this.hashCode(), other?.hashCode())
    }

    @PrePersist
    fun prePersist() {
        this.products.forEach { product -> product.order = this }
        this.shipping?.let { it.order = this }
    }

    @PreUpdate
    fun preUpdate() {
        this.products.forEach { product -> product.order = this }
        this.shipping?.let { it.order = this }
        this.updatedAt = LocalDateTime.now()
    }
}
