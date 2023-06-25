package com.trip.server.overpass;

import com.trip.server.overpass.model.Element;
import de.westnordost.osmapi.ApiResponseReader;
import de.westnordost.osmapi.overpass.OverpassMapDataApi;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class Connector {

    private final OverpassMapDataApi overpassMapDataApi;

    private final ApiResponseReader<List<Element>> apiResponseReader;

    @Cacheable(cacheNames = "overpassResponseCache", key = "#query")
    public List<Element> getResponse(String query) {
        log.debug("Overpass query: {}", query);
        return overpassMapDataApi.query(query, apiResponseReader);
    }

}
