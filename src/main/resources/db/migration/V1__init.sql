CREATE TABLE users
(
    id                  BIGSERIAL PRIMARY KEY,
    username            VARCHAR(30) NOT NULL UNIQUE,
    password            VARCHAR(80) NOT NULL,
    email               VARCHAR(50) UNIQUE,
    created_at          TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    registration_status VARCHAR(30) NOT NULL,
    last_login          TIMESTAMP,
    deleted             BOOLEAN     NOT NULL DEFAULT false
);

CREATE TABLE posts
(
    id         BIGSERIAL PRIMARY KEY,
    user_id    INTEGER      NOT NULL,
    title      VARCHAR(255) NOT NULL,
    content    TEXT         NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted    BOOLEAN      NOT NULL DEFAULT FALSE,
    likes      INTEGER      NOT NULL DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    UNIQUE (title)
);

INSERT INTO users(username, password, email, created_at, updated_at, registration_status, last_login, deleted)
VALUES ('first_user', 'password1', 'first_user@gmail.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE',
        CURRENT_TIMESTAMP, false),
       ('second_user', 'password2', 'second_user@gmail.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE',
        CURRENT_TIMESTAMP, false),
       ('third_user', 'password3', 'third_user@gmail.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE',
        CURRENT_TIMESTAMP, false);

INSERT INTO posts (user_id, title, content, created_at, updated_at, deleted, likes)
VALUES (1, 'First Post', 'This is content of first post', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, 10),
       (2, 'Second Post', 'This is content of second post', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, 3);

