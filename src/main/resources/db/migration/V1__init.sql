CREATE TABLE users
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username   VARCHAR(500)                        NOT NULL,
    birth_date DATE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    password   VARCHAR(500)                        NOT NULL
);

CREATE TABLE emails
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT              NOT NULL,
    email   VARCHAR(200) UNIQUE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE phones
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT             NOT NULL,
    phone   VARCHAR(13) UNIQUE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE accounts
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT         NOT NULL,
    balance DECIMAL(19, 2) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE INDEX idx_email_user_id ON emails (user_id);
CREATE INDEX idx_phone_user_id ON phones (user_id);
CREATE INDEX idx_account_user_id ON accounts (user_id);

CREATE TABLE refresh_token
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    user_id     BIGINT       NOT NULL UNIQUE,
    token       VARCHAR(255) NOT NULL UNIQUE,
    expiry_date TIMESTAMP    NOT NULL,

    CONSTRAINT fk_refresh_user FOREIGN KEY (user_id) REFERENCES users (id)
);

