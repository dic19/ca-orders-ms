package com.orders.domain;

import java.util.Optional;
import java.util.UUID;

public class Owner {
    private final Country country;
    private final OwnerType type;
    private final UUID uniqueID;

    private Owner(Country country, OwnerType type, UUID uniqueID) {
        this.country = country;
        this.type = type;
        this.uniqueID = uniqueID;
    }

    public Country getCountry() {
        return country;
    }

    public OwnerType getType() {
        return type;
    }

    public UUID getUniqueID() {
        return uniqueID;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Country country, OwnerType type, String uniqueID) {
        return builder(country, type, UUID.fromString(uniqueID));
    }

    public static Builder builder(Country country, OwnerType type, UUID uniqueID) {
        return new Builder(country, type, uniqueID);
    }

    public static class Builder {
        private Country country = Country.NOOP;
        private OwnerType type = OwnerType.NOOP;
        private UUID uniqueID;

        private Builder() {
        }

        private Builder(Country country, OwnerType type, UUID uniqueID) {
            this.country = country;
            this.type = type;
            this.uniqueID = uniqueID;
        }

        public Builder withCountry(Country country) {
            this.country = Optional.ofNullable(country).orElse(Country.NOOP);
            return this;
        }

        public Builder withType(OwnerType type) {
            this.type = Optional.ofNullable(type).orElse(OwnerType.NOOP);
            return this;
        }

        public Builder withUniqueID(UUID uniqueID) {
            this.uniqueID = uniqueID;
            return this;
        }

        public Builder withUniqueID(String uniqueID) {
            return this.withUniqueID(UUID.fromString(uniqueID));
        }

        public Owner build() {
            return new Owner(this.country, this.type, this.uniqueID);
        }
    }
}
