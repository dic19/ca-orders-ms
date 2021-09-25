package com.orders.domain;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;

public class Order {
    private Country country;
    private OffsetDateTime createdAt;
    private Currency currency;
    private Set<Product> products;
    private String referenceID;
    private Boolean shippingRequired;
    private Shipping shipping;
    private OrderStatus status;
    private Owner owner;
    private UUID uniqueID;
    private OffsetDateTime updatedAt;

    private Order() {
        super();
    }

    public Country getCountry() {
        return country;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public Currency getCurrency() {
        return currency;
    }

    public BigDecimal getDiscounts() {
        return this.products.stream()
            .map(Product::getDiscount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Set<Product> getProducts() {
        return products;
    }

    public String getReferenceID() {
        return referenceID;
    }

    public Boolean getShippingRequired() {
        return shippingRequired;
    }

    public Optional<Shipping> getShipping() {
        return Optional.ofNullable(shipping);
    }

    public OrderStatus getStatus() {
        return status;
    }

    public BigDecimal getSubtotal() {
        return this.products.stream()
            .map(Product::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTaxes() {
        return this.products.stream()
            .map(Product::getTaxes)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotal() {
        final var shippingCost = this.getShipping()
            .map(Shipping::getCost)
            .orElse(BigDecimal.ZERO);
        return this.products.stream()
            .map(Product::getTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .add(shippingCost);
    }

    public Owner getOwner() {
        return owner;
    }

    public UUID getUniqueID() {
        return uniqueID;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Order otherOrder) {
        final var builder = new Builder();
        Optional.ofNullable(otherOrder).ifPresent(order -> {
            order.getShipping()
                .ifPresent(builder::withShipping);
            builder
                .withCountry(order.country)
                .withCurrency(order.currency)
                .withOwner(order.owner)
                .withProducts(order.products)
                .withReferenceID(order.referenceID)
                .withStatus(order.status)
                .withUniqueID(order.uniqueID);
        });
        return builder;
    }

    public static class Builder {
        private static final Country DEFAULT_COUNTRY = Country.NOOP;
        private static final Currency DEFAULT_CURRENCY = Currency.NOOP;
        private static final Set<Product> DEFAULT_PRODUCTS = Set.of();
        private static final String DEFAULT_REFERENCE_ID = "";
        private static final boolean DEFAULT_SHIPPING_REQUIRED = false;

        private Country country = DEFAULT_COUNTRY;
        private OffsetDateTime createdAt;
        private Currency currency = DEFAULT_CURRENCY;
        private Set<Product> products = DEFAULT_PRODUCTS;
        private String referenceID = DEFAULT_REFERENCE_ID;
        private Boolean shippingRequired = DEFAULT_SHIPPING_REQUIRED;
        private Shipping shipping;
        private OrderStatus status;
        private Owner owner;
        private UUID uniqueID;
        private OffsetDateTime updatedAt;

        private Builder() {
        }

        public Builder withCountry(Country country) {
            this.country = Optional.ofNullable(country).orElse(DEFAULT_COUNTRY);
            return this;
        }

        public Builder wasCreatedAt(OffsetDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder withCurrency(Currency currency) {
            this.currency = Optional.ofNullable(currency).orElse(DEFAULT_CURRENCY);
            return this;
        }

        public Builder withOwner(Owner owner) {
            this.owner = owner;
            return this;
        }

        public Builder withProducts(Set<Product> products) {
            this.products = Optional.ofNullable(products).orElse(DEFAULT_PRODUCTS);
            return this;
        }

        public Builder withReferenceID(String referenceID) {
            this.referenceID = Optional.ofNullable(referenceID).orElse(DEFAULT_REFERENCE_ID);
            return this;
        }

        public Builder withShipping(Shipping shipping) {
            this.shippingRequired = true;
            this.shipping = shipping;
            return this;
        }

        public Builder withStatus(OrderStatus status) {
            this.status = status;
            return this;
        }

        public Builder withUniqueID(UUID uniqueID) {
            this.uniqueID = uniqueID;
            return this;
        }

        public Builder wasUpdatedAt(OffsetDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Order build() {
            final var order = new Order();
            order.country = this.country;
            order.createdAt = Optional.ofNullable(this.createdAt).orElse(OffsetDateTime.now());
            order.currency = this.currency;
            order.products = Collections.unmodifiableSet(this.products);
            order.referenceID = this.referenceID;
            order.shippingRequired = this.shippingRequired;
            order.shipping = this.shipping;
            order.status = Optional.ofNullable(this.status).orElse(OrderStatus.CREATED);
            order.owner = this.owner;
            order.uniqueID = Optional.ofNullable(this.uniqueID).orElse(UUID.randomUUID());
            order.updatedAt = Optional.ofNullable(this.updatedAt).orElse(OffsetDateTime.now());
            return order;
        }
    }
}
