package com.orders.domain;

import java.util.Optional;

public class Address {
    private String city;
    private Country country;
    private String description;
    private String neighborhood;
    private String number;
    private String state;
    private String street;
    private String zip;

    private Address() {
        super();
    }

    public String getCity() {
        return city;
    }

    public Country getCountry() {
        return country;
    }

    public String getDescription() {
        return description;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public String getNumber() {
        return number;
    }

    public String getState() {
        return state;
    }

    public String getStreet() {
        return street;
    }

    public String getZip() {
        return zip;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private static final String DEFAULT_CITY = "";
        private static final Country DEFAULT_COUNTRY = Country.NOOP;
        private static final String DEFAULT_DESCRIPTION = "";
        private static final String DEFAULT_NEIGHBORHOOD = "";
        private static final String DEFAULT_NUMBER = "";
        private static final String DEFAULT_STATE = "";
        private static final String DEFAULT_STREET = "";
        private static final String DEFAULT_ZIP = "";

        private String city = DEFAULT_CITY;
        private Country country = DEFAULT_COUNTRY;
        private String description = DEFAULT_DESCRIPTION;
        private String neighborhood = DEFAULT_NEIGHBORHOOD;
        private String number = DEFAULT_NUMBER;
        private String state = DEFAULT_STATE;
        private String street = DEFAULT_STREET;
        private String zip = DEFAULT_ZIP;

        private Builder() {
        }

        public Builder withCity(String city) {
            this.city = Optional.ofNullable(city).orElse(DEFAULT_CITY);
            return this;
        }

        public Builder withCountry(Country country) {
            this.country = Optional.ofNullable(country).orElse(Country.NOOP);
            return this;
        }

        public Builder withDescription(String description) {
            this.description = Optional.ofNullable(description).orElse(DEFAULT_DESCRIPTION);
            return this;
        }

        public Builder withNeighborhood(String neighborhood) {
            this.neighborhood = Optional.ofNullable(neighborhood).orElse(DEFAULT_NEIGHBORHOOD);
            return this;
        }

        public Builder withNumber(String number) {
            this.number = Optional.ofNullable(number).orElse(DEFAULT_NUMBER);
            return this;
        }

        public Builder withState(String state) {
            this.state = Optional.ofNullable(state).orElse(DEFAULT_STATE);
            return this;
        }

        public Builder withStreet(String street) {
            this.street = Optional.ofNullable(street).orElse(DEFAULT_STREET);
            return this;
        }

        public Builder withZip(String zip) {
            this.zip = Optional.ofNullable(zip).orElse(DEFAULT_ZIP);
            return this;
        }

        public Address build() {
            final var address = new Address();
            address.city = this.city;
            address.country = this.country;
            address.description = this.description;
            address.neighborhood = this.neighborhood;
            address.number = this.number;
            address.state = this.state;
            address.street = this.street;
            address.zip = this.zip;
            return address;
        }
    }
}
