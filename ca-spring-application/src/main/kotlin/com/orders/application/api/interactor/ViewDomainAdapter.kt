package com.orders.application.api.interactor

import com.authentication.domain.Credentials
import com.orders.application.api.model.*
import com.orders.domain.Address
import com.orders.domain.Order
import com.orders.domain.Product
import com.orders.domain.Shipping
import org.springframework.http.HttpHeaders
import java.util.*
import java.util.stream.Collectors

object ViewDomainAdapter {

    fun toCredentials(headers: HttpHeaders): Credentials {
        val authorization = headers.getFirst(HttpHeaders.AUTHORIZATION)
        val apiKey = headers.getFirst("X-API-Key")
        if (authorization.isNullOrBlank().not()) {
            return Credentials.builder().jwtCredentials(authorization)
        }
        if (apiKey.isNullOrBlank().not()) {
            return Credentials.builder().apiKeyCredentials(apiKey)
        }
        return Credentials.builder().noop();
    }

    fun toOrder(view: CreateOrderRequest): Order {
        val products = view.products.stream()
            .map(this::toProduct)
            .collect(Collectors.toSet())

        val order = Order.builder()
            .withCurrency(view.currency)
            .withProducts(products)
            .withReferenceID(view.referenceID)

        Optional.ofNullable(view.shipping)
            .map(this::toShipping)
            .ifPresent(order::withShipping)

        return order.build()
    }

    fun toOrder(view: UpdateOrderRequest): Order {
        val products = view.products.stream()
            .map(this::toProduct)
            .collect(Collectors.toSet())

        val order = Order.builder()
            .withProducts(products)

        Optional.ofNullable(view.shipping)
            .map(this::toShipping)
            .ifPresent(order::withShipping)

        return order.build()
    }

    fun toProduct(view: ProductView): Product {
        return Product.builder()
            .withCode(view.code)
            .withDiscount(view.discount)
            .withImage(view.image)
            .withName(view.name)
            .withPrice(view.price)
            .withQuantity(view.quantity)
            .withTaxes(view.taxes)
            .build()
    }

    private fun toShipping(view: ShippingView): Shipping {
        val shipping = Shipping.builder()
            .withCost(view.cost)
            .withMethod(view.method)

        Optional.ofNullable(view.address)
            .map(this::toAddress)
            .ifPresent(shipping::withAddress)

        return shipping.build()
    }

    private fun toAddress(view: AddressView): Address {
        return Address.builder()
            .withCity(view.city)
            .withCountry(view.country)
            .withDescription(view.description)
            .withNeighborhood(view.neighborhood)
            .withNumber(view.number)
            .withState(view.state)
            .withStreet(view.street)
            .withZip(view.zip)
            .build()
    }
}