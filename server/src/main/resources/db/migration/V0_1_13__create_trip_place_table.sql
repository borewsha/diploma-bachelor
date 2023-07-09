CREATE TABLE trip_place
(
    id       SERIAL8 NOT NULL,
    trip_id  BIGINT  NOT NULL,
    place_id BIGINT  NOT NULL,
    date     DATE    NOT NULL,
    "order"  INTEGER NOT NULL,
    CONSTRAINT pk_trip_place PRIMARY KEY (id)
);

ALTER TABLE trip_place
    ADD CONSTRAINT FK_TRIP_PLACE_ON_TRIP FOREIGN KEY (trip_id) REFERENCES trip (id),
    ADD CONSTRAINT FK_TRIP_PLACE_ON_PLACE FOREIGN KEY (place_id) REFERENCES place (id);
