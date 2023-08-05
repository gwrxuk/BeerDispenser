CREATE TABLE IF NOT EXISTS dispenser (
        id UUID PRIMARY KEY NOT NULL,
        flow_volume DOUBLE PRECISION NOT NULL,
        is_open BOOLEAN NOT NULL,
        updated_at BIGINT NOT NULL,
        brand VARCHAR (100) NOT NULL,
        price DOUBLE PRECISION NOT NULL
);
