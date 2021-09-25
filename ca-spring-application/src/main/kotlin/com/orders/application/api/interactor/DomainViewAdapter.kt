package com.orders.application.api.interactor

import com.orders.application.api.model.*
import com.orders.domain.Order
import com.orders.domain.Product
import com.orders.domain.Shipping
import java.util.stream.Collectors

object DomainViewAdapter {

    fun toOrderResponse(order: Order) = OrderResponse(
        country = order.country,
        createdAt = order.createdAt,
        currency = order.currency,
        owner = OwnerView(
            country = order.owner.country,
            type = order.owner.type,
            uniqueID = order.owner.uniqueID
        ),
        products = order.products.stream()
            .map(this::toProductView)
            .collect(Collectors.toSet()),
        referenceID = order.referenceID,
        shippingRequired = order.shippingRequired,
        shipping = toShippingView(order.shipping.orElse(null)),
        status = order.status,
        subtotal = order.subtotal,
        total = order.total,
        uniqueID = order.uniqueID,
        updatedAt = order.updatedAt
    )

    private fun toProductView(product: Product) = ProductView(
        code = product.code,
        discount = product.discount,
        image = product.image,
        name = product.image,
        price = product.price,
        quantity = product.quantity,
        taxes = product.taxes
    )

    private fun toShippingView(shipping: Shipping?) = shipping?.let {
        ShippingView(
            address = it.address?.let { address ->
                AddressView(
                    city = address.city,
                    country = address.country,
                    description = address.description,
                    neighborhood = address.neighborhood,
                    number = address.number,
                    state = address.state,
                    street = address.street,
                    zip = address.zip
                )
            },
            cost = it.cost,
            method = it.method
        )
    }
}