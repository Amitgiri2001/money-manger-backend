CREATE TABLE txn_classification (
    id BIGSERIAL PRIMARY KEY,

    level VARCHAR(50) NOT NULL,

    name VARCHAR(100) NOT NULL,

    description VARCHAR(200),

    user_id BIGINT,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);