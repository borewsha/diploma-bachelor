package com.trip.server.overpass.repository;

import com.trip.server.overpass.entity.City;
import com.trip.server.overpass.model.Element;
import com.trip.server.overpass.query.Node;
import com.trip.server.overpass.query.QueryBuilder;
import com.trip.server.overpass.reader.JsonResponseReader;
import de.westnordost.osmapi.overpass.OverpassMapDataApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component("overpassCityRepository")
public class CityRepository {

    private final OverpassMapDataApi overpassMapDataApi;

    private final JsonResponseReader jsonResponseReader;

    private final ModelMapper modelMapper;

    private final QueryBuilder queryBuilder;

    public Optional<City> findById(String id) {
        var query = queryBuilder.json()
                .id(id)
                .out();
        var response = getResponse(query);

        return response.stream()
                .map(e -> modelMapper.map(e, City.class))
                .findFirst();
    }

    public List<City> findAll() {
        var request = queryBuilder.json()
                .defaultArea()
                .set(Node.withTags("place~'city|town'").area())
                .out();

        return getResponse(request).stream()
                .map(e -> modelMapper.map(e, City.class))
                .collect(Collectors.toList());
    }

    private List<Element> getResponse(String query) {
        log.debug("Overpass query: {}", query);
        return overpassMapDataApi.query(query, jsonResponseReader);
    }

}
