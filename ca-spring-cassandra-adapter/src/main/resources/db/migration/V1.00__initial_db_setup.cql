--
-- Product
--
CREATE TYPE IF NOT EXISTS marketplace.product (
    code text,
    discount decimal,
    image text,
    name text,
    price decimal,
    quantity varint,
    taxes decimal
);

--
-- Shipping
--
CREATE TYPE IF NOT EXISTS marketplace.shipping (
    city text,
    cost decimal,
    country text,
    description text,
    method text,
    neighborhood text,
    number text,
    state text,
    street text,
    zip text
);

--
-- Orders
--
CREATE TABLE IF NOT EXISTS marketplace.orders (
    country text,
    currency text,
    created_at timestamp,
    owner_type text,
    owner_id uuid,
    products set<FROZEN <product>>,
    reference_id text,
    shipping_required boolean,
    shipping shipping,
    status text,
    unique_id uuid,
    updated_at timestamp,
    PRIMARY KEY (owner_type, owner_id, unique_id)
);

CREATE TABLE IF NOT EXISTS marketplace.orders_by_reference_id (
    owner_type text,
    owner_id uuid,
    reference_id text,
    unique_id uuid,
    PRIMARY KEY (owner_type, owner_id, reference_id)
)