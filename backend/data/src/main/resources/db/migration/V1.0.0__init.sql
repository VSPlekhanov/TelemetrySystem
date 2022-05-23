CREATE TABLE IF NOT EXISTS users (
    id bigserial PRIMARY KEY,
    name character varying(255) UNIQUE NOT NULL,
    password character varying(255) NOT NULL
);

CREATE TYPE event_types AS ENUM
    ('STARTUP', 'SHUTDOWN', 'UPDATE');

CREATE TABLE IF NOT EXISTS events (
    id bigserial NOT NULL,
    event_type event_types NOT NULL,
    user_id bigint NOT NULL,
    created_at timestamp NOT NULL,
    event_data jsonb NOT NULL,
    CONSTRAINT event_pk PRIMARY KEY (id),
    CONSTRAINT event_user_fk FOREIGN KEY (user_id) REFERENCES users(id)
);
