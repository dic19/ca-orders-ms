package com.orders.domain;

import java.math.BigDecimal;
import java.util.Optional;

public class Shipping {
    private Address address;
    private BigDecimal cost;
    private String method;

    private Shipping() {
        super();
    }

    public Address getAddress() {
        return address;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public String getMethod() {
        return method;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private static final BigDecimal DEFAULT_COST = BigDecimal.ZERO;
        private static final String DEFAULT_METHOD = "";

        private Address address;
        private BigDecimal cost = DEFAULT_COST;
        private String method = DEFAULT_METHOD;

        private Builder() {
        }

        public Builder withAddress(Address address) {
            this.address = address;
            return this;
        }

        public Builder withCost(BigDecimal cost) {
            this.cost = Optional.ofNullable(cost).orElse(DEFAULT_COST);
            return this;
        }

        public Builder withMethod(String method) {
            this.method = Optional.ofNullable(method).orElse(DEFAULT_METHOD);
            return this;
        }

        public Shipping build() {
            final var shipping = new Shipping();
            shipping.address = this.address;
            shipping.cost = this.cost;
            shipping.method = this.method;
            return shipping;
        }
    }
}
