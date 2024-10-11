--V1__initial_setup.sql

CREATE TABLE IF NOT EXISTS users (
  id                BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  uuid              VARCHAR(32) NOT NULL UNIQUE,
  is_deleted        BOOLEAN NOT NULL,
  first_name        VARCHAR(32) NOT NULL,
  last_name         VARCHAR(32) NOT NULL,
  email             VARCHAR(32) NOT NULL UNIQUE,
  password          VARCHAR(32) NOT NULL UNIQUE,
  password2         VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE user_roles (
    user_id         BIGINT NOT NULL,
    roles           VARCHAR(255) NOT NULL CHECK (roles IN ('ROLE_USER','ROLE_ADMIN')),

    CONSTRAINT fk_user_roles_on_user
        FOREIGN KEY (user_id) REFERENCES users (id)
);