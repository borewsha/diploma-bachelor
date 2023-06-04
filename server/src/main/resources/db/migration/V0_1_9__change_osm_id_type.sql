ALTER TABLE city
    ALTER COLUMN osm_id TYPE VARCHAR USING 'N' || osm_id;

ALTER TABLE place
    ALTER COLUMN osm_id TYPE VARCHAR USING 'W' || osm_id;
