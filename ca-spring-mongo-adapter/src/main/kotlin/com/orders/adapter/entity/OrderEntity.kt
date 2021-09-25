package com.orders.adapter.entity

import com.orders.domain.Country
import com.orders.domain.Currency
import com.orders.domain.OrderStatus
import com.orders.domain.OwnerType
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime
import java.util.*

@Document(collection = "orders")
data class OrderEntity(
    @Id
    val id: String? = null,

    @Field("country")
    val country: Country,

    @Field("currency")
    val currency: Currency,

    @Field("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Field("owner_type")
    val ownerType: OwnerType,

    @Field("owner_id")
    val ownerID: UUID,

    @Field("products")
    val products: MutableSet<ProductEntity> = mutableSetOf(),

    @Field("reference_id")
    val referenceID: String,

    @Field("shipping_required")
    var shippingRequired: Boolean = false,

    @Field("shipping")
    var shipping: ShippingEntity? = null,

    @Field("status")
    val status: OrderStatus = OrderStatus.CREATED,

    @Field("unique_id")
    val uniqueID: UUID,

    @Field("updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {

    override fun hashCode(): Int {
        return uniqueID.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return Objects.equals(this.hashCode(), other?.hashCode())
    }
}

