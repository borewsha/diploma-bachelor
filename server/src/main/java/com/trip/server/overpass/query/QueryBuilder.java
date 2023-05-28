package com.trip.server.overpass.query;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QueryBuilder {

    @Value("${overpass.default-area-tags}")
    private String defaultAreaTags;

    public Query json() {
        return new Query("json", defaultAreaTags);
    }

}
