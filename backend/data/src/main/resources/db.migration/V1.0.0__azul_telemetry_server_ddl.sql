SET search_path TO azul_schema;

CREATE TABLE IF NOT EXISTS users (
    id serial NOT NULL,
    name text NOT NULL,
    CONSTRAINT user_pk PRIMARY KEY (id)
);

CREATE TYPE event_types AS ENUM
    ('STARTUP', 'SHUTDOWN', 'UPDATE');

CREATE TABLE IF NOT EXISTS events (
    id serial NOT NULL,
    event_type event_types NOT NULL,
    user_id int NOT NULL,
    created_at timestamp NOT NULL,
    event_data json NOT NULL,
    CONSTRAINT event_pk PRIMARY KEY (id),
    CONSTRAINT event_user_fk FOREIGN KEY (user_id) REFERENCES users(id)
);