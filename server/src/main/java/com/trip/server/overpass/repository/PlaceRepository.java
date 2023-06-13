package com.trip.server.overpass.repository;

import com.trip.server.database.enumeration.PlaceType;
import com.trip.server.overpass.entity.Place;
import com.trip.server.overpass.model.Element;
import com.trip.server.overpass.model.GeoFilters;
import com.trip.server.overpass.query.*;
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
@Component("overpassPlaceRepository")
public class PlaceRepository {

    private final OverpassMapDataApi overpassMapDataApi;

    private final JsonResponseReader jsonResponseReader;

    private final ModelMapper modelMapper;

    private final QueryBuilder queryBuilder;

    public Optional<Place> findById(String id) {
        var query = queryBuilder.json()
                .id(id)
                .out();
        var response = getResponse(query);

        return response.stream()
                .map(e -> modelMapper.map(e, Place.class))
                .findFirst();
    }

    public List<Place> findBuildingsByCity(String city) {
        var request = queryBuilder.json()
                .area(Area.withTags("place~'city|town'", "name='" + city + "'"))
                .set(Way.withTags("building", "'addr:street'", "'addr:housenumber'").area())
                .out();

        return getResponse(request).stream()
                .map(e -> modelMapper.map(e, Place.class, PlaceType.BUILDING.name()))
                .collect(Collectors.toList());
    }

    public List<Place> findTourismByCity(String city, GeoFilters filters) {
        var request = queryBuilder.json()
                .boundingBox(filters.getBbox())
                .area(Area.withTags("place~'city|town'", "name='" + city + "'"))
                .set(NodeWay.withTags(
                        "tourism",
                        "tourism!~'^(hotel|hostel|information|apartment|guest_house)$'",
                        "~'^(name|description)$'~'.'"
                ).area())
                .out();

        return getResponse(request).stream()
                .map(e -> modelMapper.map(e, Place.class, PlaceType.TOURISM.name()))
                .collect(Collectors.toList());
    }

    private List<Element> getResponse(String query) {
        log.debug("Overpass query: {}", query);
        return overpassMapDataApi.query(query, jsonResponseReader);
    }

}
