CREATE TABLE IF NOT EXISTS authors(
     id BIGINT PRIMARY KEY,
     fullname VARCHAR(255) NOT NULL,
     birth_date DATE
);

CREATE TABLE IF NOT EXISTS books(
    id BIGINT PRIMARY KEY,
    title VARCHAR(512) NOT NULL,
    description VARCHAR(1024),
    isbn INTEGER NOT NULL,
    year INTEGER,
    copies_total INTEGER,
    copies_available INTEGER,
    author_id BIGINT REFERENCES authors(id) ON DELETE SET NULL
);
