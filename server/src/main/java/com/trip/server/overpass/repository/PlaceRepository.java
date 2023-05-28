package com.trip.server.overpass.repository;

import com.trip.server.overpass.entity.Place;
import com.trip.server.overpass.model.Element;
import com.trip.server.overpass.query.QueryBuilder;
import com.trip.server.overpass.reader.JsonResponseReader;
import com.trip.server.util.PageUtil;
import de.westnordost.osmapi.overpass.OverpassMapDataApi;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component("overpassPlaceRepository")
@RequiredArgsConstructor
public class PlaceRepository {

    private final OverpassMapDataApi overpassMapDataApi;

    private final JsonResponseReader jsonResponseReader;

    private final ModelMapper modelMapper;

    private final QueryBuilder queryBuilder;

    public Optional<Place> findBuildingById(Long id) {
        var query = queryBuilder.json()
                .way(id)
                .out();
        var response = overpassMapDataApi.query(query, jsonResponseReader);

        return response.stream()
                .map(e -> modelMapper.map(e, Place.class))
                .findFirst();
    }

    public Optional<Place> findHotelById(Long id) {
        var query = queryBuilder.json()
                .node(id)
                .out();
        var response = overpassMapDataApi.query(query, jsonResponseReader);

        return response.stream()
                .map(e -> modelMapper.map(e, Place.class))
                .findFirst();
    }

    public Page<Place> findBuildings(String city, Pageable pageable) {
        var content = streamBuildings(city)
                .toList();

        return PageUtil.paginate(content, pageable);
    }

    public Page<Place> findBuildingsByAddressLike(String city, String search, Pageable pageable) {
        var content = streamBuildings(city)
                .filter(p -> p.getAddress() != null && p.getAddress().toLowerCase().matches(".*" + search.toLowerCase() + ".*"))
                .toList();

        return PageUtil.paginate(content, pageable);
    }

    private Stream<Place> streamBuildings(String city) {
        var query = queryBuilder.json()
                .area("place~'city|town'", "name='" + city + "'")
                .way("building", "'addr:street'", "'addr:housenumber'")
                .out();

        return overpassMapDataApi.query(query, jsonResponseReader).stream()
                .map(e -> modelMapper.map(e, Place.class))
                .peek(p -> p.setType("building"));
    }

}
