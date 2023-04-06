CREATE TABLE "user"
(
    id            SERIAL8                  NOT NULL,
    role_id       BIGINT,
    full_name     VARCHAR(255),
    registered_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE "user"
    ADD CONSTRAINT FK_USER_ON_ROLE FOREIGN KEY (role_id) REFERENCES user_role (id);