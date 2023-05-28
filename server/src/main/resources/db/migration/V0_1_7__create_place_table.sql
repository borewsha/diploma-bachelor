CREATE TABLE place
(
    id       SERIAL8          NOT NULL,
    type     VARCHAR          NOT NULL,
    name     VARCHAR          NULL,
    address  VARCHAR          NOT NULL,
    lat      DOUBLE PRECISION NOT NULL,
    lon      DOUBLE PRECISION NOT NULL,
    image_id BIGINT           NULL,
    osm_id   BIGINT           NULL,
    CONSTRAINT pk_place PRIMARY KEY (id)
);
