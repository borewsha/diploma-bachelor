CREATE TABLE user_credential
(
    id       SERIAL8      NOT NULL,
    user_id  BIGINT,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255),
    full_name VARCHAR(255),
    CONSTRAINT pk_user_credential PRIMARY KEY (id)
);

ALTER TABLE user_credential
    ADD CONSTRAINT FK_USER_CREDENTIAL_ON_USER FOREIGN KEY (user_id) REFERENCES "user" (id);