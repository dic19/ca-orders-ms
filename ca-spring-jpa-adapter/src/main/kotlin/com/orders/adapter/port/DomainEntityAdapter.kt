package com.orders.adapter.port

import com.orders.adapter.entity.ProductEntity
import com.orders.adapter.entity.ShippingEntity
import com.orders.domain.Product
import com.orders.domain.Shipping

object DomainEntityAdapter {

    fun toProductEntity(product: Product) = ProductEntity(
        code = product.code,
        discount = product.discount,
        image = product.image,
        name = product.name,
        price = product.price,
        quantity = product.quantity,
        taxes = product.taxes
    )

    fun toShippingEntity(shipping: Shipping) = ShippingEntity(
        city = shipping.address.city,
        cost = shipping.cost,
        country = shipping.address.country,
        description = shipping.address.description,
        method = shipping.method,
        neighborhood = shipping.address.neighborhood,
        number = shipping.address.number,
        state = shipping.address.state,
        street = shipping.address.street,
        zip = shipping.address.zip
    )

    fun mapShippingData(shipping: Shipping, shippingEntity: ShippingEntity) = shippingEntity.apply {
        this.city = shipping.address.city
        this.cost = shipping.cost
        this.country = shipping.address.country
        this.description = shipping.address.description
        this.method = shipping.method
        this.neighborhood = shipping.address.neighborhood
        this.number = shipping.address.number
        this.state = shipping.address.state
        this.street = shipping.address.street
        this.zip = shipping.address.zip
    }
}