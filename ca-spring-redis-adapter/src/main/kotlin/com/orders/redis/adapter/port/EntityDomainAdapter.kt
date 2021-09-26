package com.orders.redis.adapter.port

import com.orders.redis.adapter.entity.OrderEntity
import com.orders.redis.adapter.entity.ProductEntity
import com.orders.redis.adapter.entity.ShippingEntity
import com.orders.domain.*
import java.time.ZoneOffset
import java.util.*
import java.util.stream.Collectors

object EntityDomainAdapter {

    fun toOrder(entity: OrderEntity): Order {
        val products = entity.products.stream()
            .map(this::toProduct)
            .collect(Collectors.toSet())

        val owner = Owner.builder(entity.country, entity.ownerType, entity.ownerID)
            .build()

        val builder = Order.builder()
            .withCountry(entity.country)
            .withCurrency(entity.currency)
            .withOwner(owner)
            .withProducts(products)
            .withReferenceID(entity.referenceID)
            .withStatus(entity.status)
            .withUniqueID(entity.uniqueID)
            .wasCreatedAt(entity.createdAt.atOffset(ZoneOffset.UTC))
            .wasUpdatedAt(entity.updatedAt.atOffset(ZoneOffset.UTC))

        if (entity.shippingRequired && Objects.nonNull(entity.shipping)) {
            builder.withShipping(toShipping(entity.shipping!!))
        }

        return builder.build()
    }

    private fun toProduct(entity: ProductEntity): Product {
        return Product.builder()
            .withCode(entity.code)
            .withDiscount(entity.discount)
            .withImage(entity.image)
            .withName(entity.name)
            .withPrice(entity.price)
            .withQuantity(entity.quantity)
            .withTaxes(entity.taxes)
            .build()
    }

    private fun toShipping(entity: ShippingEntity): Shipping {
        val address = Address.builder()
            .withCity(entity.city)
            .withCountry(entity.country)
            .withDescription(entity.description)
            .withNeighborhood(entity.neighborhood)
            .withNumber(entity.number)
            .withState(entity.state)
            .withStreet(entity.street)
            .withZip(entity.zip)
            .build()

        return Shipping.builder()
            .withAddress(address)
            .withCost(entity.cost)
            .withMethod(entity.method)
            .build()
    }
}