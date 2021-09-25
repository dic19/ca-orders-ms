package com.orders.domain;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

public class Product {
    private String code;
    private BigDecimal discount;
    private String image;
    private String name;
    private BigDecimal price;
    private BigInteger quantity;
    private BigDecimal taxes;

    private Product() {
        super();
    }

    public String getCode() {
        return code;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigInteger getQuantity() {
        return quantity;
    }

    public BigDecimal getTaxes() {
        return taxes;
    }

    public BigDecimal getSubtotal() {
        return this.price.multiply(new BigDecimal(this.quantity));
    }

    public BigDecimal getTotal() {
        return this.price.multiply(new BigDecimal(this.quantity))
            .add(this.taxes)
            .subtract(this.discount);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private static final String DEFAULT_CODE = "";
        private static final BigDecimal DEFAULT_DISCOUNT = BigDecimal.ZERO;
        private static final String DEFAULT_IMAGE = "";
        private static final String DEFAULT_NAME = "";
        private static final BigDecimal DEFAULT_PRICE = BigDecimal.ZERO;
        private static final BigInteger DEFAULT_QUANTITY = BigInteger.ZERO;
        private static final BigDecimal DEFAULT_TAXES = BigDecimal.ZERO;

        private String code = DEFAULT_CODE;
        private BigDecimal discount = DEFAULT_DISCOUNT;
        private String image  = DEFAULT_IMAGE ;
        private String name = DEFAULT_NAME;
        private BigDecimal price = DEFAULT_PRICE;
        private BigInteger quantity = DEFAULT_QUANTITY;
        private BigDecimal taxes = DEFAULT_TAXES;

        private Builder() {}

        public Builder withCode(String code) {
            this.code = Optional.ofNullable(code).orElse(DEFAULT_CODE);
            return this;
        }

        public Builder withDiscount(BigDecimal discount) {
            this.discount = Optional.ofNullable(discount).orElse(DEFAULT_DISCOUNT);
            return this;
        }

        public Builder withImage(String image) {
            this.image = Optional.ofNullable(image).orElse(DEFAULT_IMAGE);
            return this;
        }

        public Builder withName(String name) {
            this.name = Optional.ofNullable(name).orElse(DEFAULT_NAME);
            return this;
        }

        public Builder withPrice(BigDecimal price) {
            this.price = Optional.ofNullable(price).orElse(DEFAULT_PRICE);
            return this;
        }

        public Builder withQuantity(BigInteger quantity) {
            this.quantity = Optional.ofNullable(quantity).orElse(DEFAULT_QUANTITY);
            return this;
        }

        public Builder withTaxes(BigDecimal taxes) {
            this.taxes = Optional.ofNullable(taxes).orElse(DEFAULT_TAXES);
            return this;
        }

        public Product build() {
            final var product = new Product();
            product.code = this.code;
            product.discount = this.discount;
            product.image = this.image;
            product.name = this.name;
            product.price = this.price;
            product.quantity = this.quantity;
            product.taxes = this.taxes;
            return product;
        }
    }
}
