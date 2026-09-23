CREATE TABLE IF NOT EXISTS authors(
     id SERIAL PRIMARY KEY,
     fullname VARCHAR(255) NOT NULL,
     birth_date DATE
);

CREATE TABLE IF NOT EXISTS books(
    id SERIAL PRIMARY KEY,
    title VARCHAR(512) NOT NULL,
    description VARCHAR(1024),
    isbn INTEGER NOT NULL,
    year INTEGER,
    copies INTEGER,
    author_id INTEGER REFERENCES authors(id) ON DELETE SET NULL
);
