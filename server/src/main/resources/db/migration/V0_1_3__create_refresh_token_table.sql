CREATE TABLE refresh_token
(
    id      SERIAL8      NOT NULL,
    user_id BIGINT       NOT NULL,
    token   VARCHAR(255) NOT NULL,
    CONSTRAINT pk_refresh_token PRIMARY KEY (id)
);

ALTER TABLE refresh_token
    ADD CONSTRAINT FK_REFRESH_TOKEN_ON_USER FOREIGN KEY (user_id) REFERENCES "user" (id);
