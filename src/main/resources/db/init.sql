-- CREATE TABLE
CREATE TABLE books (
    id SERIAL PRIMARY KEY,
    title VARCHAR NOT NULL,
    genre VARCHAR,
    pages INTEGER,
    authors VARCHAR DEFAULT NULL
);