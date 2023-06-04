package com.trip.server.overpass.query;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class QueryBuilder {

    @Value("#{'${overpass.default-area-tags}'.split(',')}")
    private List<String> defaultAreaTags;

    public Query json() {
        return new Query(defaultAreaTags, OutputFormat.JSON);
    }

}
