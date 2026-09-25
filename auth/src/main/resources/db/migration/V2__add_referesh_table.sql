CREATE TABLE IF NOT EXISTS refresh_token(
    id BIGINT PRIMARY KEY,
    member_id BIGINT REFERENCES members(id) ON DELETE SET NULL,
    token_hash TEXT,
    expiry_date TIMESTAMPTZ  NOT NULL
);
