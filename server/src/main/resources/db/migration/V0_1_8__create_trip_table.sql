CREATE TABLE trip
(
    id               SERIAL8   NOT NULL,
    user_id          BIGINT    NOT NULL,
    city_id          BIGINT    NOT NULL,
    accommodation_id BIGINT    NULL,
    starts_at        TIMESTAMP NULL,
    ends_at          TIMESTAMP NULL,
    CONSTRAINT pk_trip PRIMARY KEY (id)
);

ALTER TABLE trip
    ADD CONSTRAINT FK_TRIP_ON_USER FOREIGN KEY (user_id) REFERENCES "user" (id),
    ADD CONSTRAINT FK_TRIP_ON_CITY FOREIGN KEY (city_id) REFERENCES city (id),
    ADD CONSTRAINT FK_TRIP_ON_PLACE FOREIGN KEY (accommodation_id) REFERENCES place (id);
