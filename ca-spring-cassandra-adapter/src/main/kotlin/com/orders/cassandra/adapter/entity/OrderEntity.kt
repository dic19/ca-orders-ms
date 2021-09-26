package com.orders.cassandra.adapter.entity

import com.orders.domain.Country
import com.orders.domain.Currency
import com.orders.domain.OrderStatus
import com.orders.domain.OwnerType
import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn
import org.springframework.data.cassandra.core.mapping.Table
import java.time.LocalDateTime
import java.util.*

@Table("orders")
data class OrderEntity(
    val country: Country,
    val currency: Currency,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @PrimaryKeyColumn(name = "owner_type", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    val ownerType: OwnerType,
    @PrimaryKeyColumn(name = "owner_id", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    val ownerID: UUID,
    val products: MutableSet<ProductType> = mutableSetOf(),
    val referenceID: String,
    var shippingRequired: Boolean = false,
    var shipping: ShippingType? = null,
    val status: OrderStatus = OrderStatus.CREATED,
    @PrimaryKeyColumn(name = "unique_id", ordinal = 2, type = PrimaryKeyType.PARTITIONED)
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

