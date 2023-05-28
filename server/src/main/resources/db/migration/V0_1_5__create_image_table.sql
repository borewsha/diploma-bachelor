CREATE TABLE image
(
    id      SERIAL8 NOT NULL,
    content BYTEA   NOT NULL,
    CONSTRAINT pk_image PRIMARY KEY (id)
);
