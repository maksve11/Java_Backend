CREATE TABLE IF NOT EXISTS owner
(
    owner_id        BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    owner_name      VARCHAR(255) NOT NULL,
    owner_birthdate DATE         NOT NULL
    );

CREATE TABLE IF NOT EXISTS cat
(
    cat_id        BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    cat_name      VARCHAR(255) NOT NULL,
    cat_birthdate DATE         NOT NULL,
    owner_id      BIGINT REFERENCES owner (owner_id) NOT NULL,
    breed        VARCHAR(255) NOT NULL,
    color        VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS flea
(
    flea_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    flea_name VARCHAR(255) NOT NULL,
    cat_id BIGINT REFERENCES cat (cat_id) NOT NULL
)