CREATE TABLE city
(
    id       SERIAL8          NOT NULL,
    name     VARCHAR          NOT NULL,
    region   VARCHAR          NOT NULL,
    lat      DOUBLE PRECISION NOT NULL,
    lon      DOUBLE PRECISION NOT NULL,
    image_id BIGINT           NULL,
    osm_id   BIGINT           NULL,
    CONSTRAINT pk_city PRIMARY KEY (id)
);
