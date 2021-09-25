-- Orders

DROP TABLE IF EXISTS orders CASCADE;

CREATE TABLE orders(
    id SERIAL PRIMARY KEY,
    country VARCHAR(10),
    currency VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    owner_type VARCHAR(100),
    owner_id BINARY(16),
    reference_id VARCHAR(255),
    shipping_required BOOLEAN,
    status VARCHAR(100),
    unique_id BINARY(16),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX idx_orders_reference
    ON orders(owner_type, owner_id, reference_id);

CREATE UNIQUE INDEX unq_orders_unique_id
    ON orders(unique_id);

CREATE INDEX idx_orders_status
    ON orders(status);

-- Shipping

DROP TABLE IF EXISTS shipping CASCADE;

CREATE TABLE shipping(
    id BIGINT UNSIGNED NOT NULL PRIMARY KEY,
    city VARCHAR(255),
    cost DECIMAL(10,2) DEFAULT 0.0,
    country VARCHAR(255),
    description VARCHAR(255),
    method VARCHAR(255),
    neighborhood VARCHAR(255),
    number VARCHAR(255),
    state VARCHAR(255),
    street VARCHAR(255),
    zip VARCHAR(255)
);

ALTER TABLE shipping
    ADD CONSTRAINT fk_shipping_orders
    FOREIGN KEY (id)
    REFERENCES orders(id);

-- Products

DROP TABLE IF EXISTS products CASCADE;

CREATE TABLE products(
    id SERIAL PRIMARY KEY,
    code VARCHAR(255),
    discount DECIMAL(10, 2) DEFAULT 0.0,
    image VARCHAR(255),
    order_id BIGINT UNSIGNED NOT NULL,
    name VARCHAR(255),
    price DECIMAL(10, 2) DEFAULT 0.0,
    quantity INTEGER DEFAULT 0,
    taxes DECIMAL(10, 2) DEFAULT 0.0
);

ALTER TABLE products
    ADD CONSTRAINT fk_products_orders
    FOREIGN KEY (order_id)
    REFERENCES orders(id);

CREATE INDEX idx_products_code
    ON products(order_id, code);