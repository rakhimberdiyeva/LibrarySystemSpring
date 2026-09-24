CREATE TABLE IF NOT EXISTS members(
    id BIGINT PRIMARY KEY,
    username VARCHAR(128) NOT NULL UNIQUE,
    password_hash TEXT,
    fullname VARCHAR(128) NOT NULL,
    email VARCHAR(128) NOT NULL UNIQUE,
    role VARCHAR(10) NOT NULL DEFAULT  'member',
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ  NOT NULL
);