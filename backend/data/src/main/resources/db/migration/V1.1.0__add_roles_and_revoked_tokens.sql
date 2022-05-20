CREATE TABLE revoked_tokens (
    id bigserial PRIMARY KEY,
    token character varying(255) UNIQUE NOT NULL
);

CREATE TABLE roles (
    id bigserial PRIMARY KEY,
    name character varying(255) UNIQUE NOT NULL
);

CREATE TABLE users_roles (
    user_id bigint NOT NULL REFERENCES users(id),
    role_id bigint NOT NULL REFERENCES roles(id),
    PRIMARY KEY (user_id, role_id)
);

-- This name should be equal to org.azul.telemetry.data.model.entity.Role.ADMIN_ROLE_NAME
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
