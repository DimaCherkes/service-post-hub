CREATE TABLE posts
(
    id      BIGSERIAL PRIMARY KEY,
    title   VARCHAR(255) NOT NULL,
    content TEXT         NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    likes   INTEGER      NOT NULL DEFAULT 0,
    UNIQUE (title)
);

INSERT INTO posts (title, content, created_at, likes) VALUES
            ('First Post', 'This is content of first post', CURRENT_TIMESTAMP, 10),
            ('Second Post', 'This is content of second post', CURRENT_TIMESTAMP, 3);

