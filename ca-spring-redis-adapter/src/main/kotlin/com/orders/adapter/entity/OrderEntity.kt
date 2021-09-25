package com.orders.adapter.entity

import com.orders.domain.Country
import com.orders.domain.Currency
import com.orders.domain.OrderStatus
import com.orders.domain.OwnerType
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed
import java.time.LocalDateTime
import java.util.*

@RedisHash("orders")
data class OrderEntity(
    @Id
    val id: String? = null,
    val country: Country,
    val currency: Currency,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Indexed
    val ownerType: OwnerType,
    @Indexed
    val ownerID: UUID,
    val products: MutableSet<ProductEntity> = mutableSetOf(),
    @Indexed
    val referenceID: String,
    var shippingRequired: Boolean = false,
    var shipping: ShippingEntity? = null,
    val status: OrderStatus = OrderStatus.CREATED,
    @Indexed
    val uniqueID: UUID,
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {

    override fun hashCode(): Int {
        return uniqueID.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return Objects.equals(this.hashCode(), other?.hashCode())
    }
}

