CREATE TABLE IF NOT EXISTS users (
    id serial NOT NULL,
    name text NOT NULL,
    CONSTRAINT user_pk PRIMARY KEY (id)
);