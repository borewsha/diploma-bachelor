package com.trip.server.overpass.repository;

import com.trip.server.database.enumeration.PlaceType;
import com.trip.server.overpass.entity.Place;
import com.trip.server.overpass.model.Element;
import com.trip.server.overpass.model.GeoFilters;
import com.trip.server.overpass.query.*;
import com.trip.server.overpass.reader.JsonResponseReader;
import com.trip.server.util.PageUtil;
import de.westnordost.osmapi.overpass.OverpassMapDataApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Component("overpassPlaceRepository")
public class PlaceRepository {

    private final OverpassMapDataApi overpassMapDataApi;

    private final JsonResponseReader jsonResponseReader;

    private final ModelMapper modelMapper;

    private final QueryBuilder queryBuilder;

    private final LevenshteinDistance distance;

    public Optional<Place> findById(String id) {
        var query = queryBuilder.json()
                .id(id)
                .out();
        var response = getResponse(query);

        return response.stream()
                .map(e -> modelMapper.map(e, Place.class))
                .findFirst();
    }

    public Page<Place> findBuildings(String city, Pageable pageable) {
        var content = streamBuildings(city)
                .toList();

        return PageUtil.paginate(content, pageable);
    }

    public List<Place> findTourism(String city, GeoFilters filters) {
        return streamTourism(city, filters)
                .toList();
    }

    public Page<Place> findBuildingsWithSearch(String city, String search, Pageable pageable) {
        var similarityComparator = getSimilarityComparator(search);
        var content = streamBuildings(city)
                .sorted(similarityComparator)
                .toList();

        return PageUtil.paginate(content, pageable);
    }

    public List<Place> findTourismWithSearch(String city, String search, GeoFilters filters) {
        var similarityComparator = getSimilarityComparator(search);

        return streamTourism(city, filters)
                .sorted(similarityComparator)
                .toList();
    }

    private List<Element> getResponse(String query) {
        log.debug("Overpass query: {}", query);
        return overpassMapDataApi.query(query, jsonResponseReader);
    }

    private Stream<Place> streamBuildings(String city) {
        var request = queryBuilder.json()
                .area(Area.withTags("place~'city|town'", "name='" + city + "'"))
                .set(Way.withTags("building", "'addr:street'", "'addr:housenumber'").area())
                .out();

        return getResponse(request).stream()
                .map(e -> modelMapper.map(e, Place.class, PlaceType.BUILDING.name()));
    }

    private Stream<Place> streamTourism(String city, GeoFilters filters) {
        var query = queryBuilder.json();

        if (filters.getBbox() != null) {
            query.boundingBox(
                    filters.getBbox().get(0),
                    filters.getBbox().get(1),
                    filters.getBbox().get(2),
                    filters.getBbox().get(3)
            );
        }

        var request = query.area(Area.withTags("place~'city|town'", "name='" + city + "'"))
                .set(NodeWay.withTags("tourism", "tourism!~'^(hotel|hostel|information|apartment)$'", "~'^(name|description)$'~'.'").area())
                .out();

        return getResponse(request).stream()
                .map(e -> modelMapper.map(e, Place.class, PlaceType.TOURISM.name()));
    }

    private Comparator<Place> getSimilarityComparator(String search) {
        return Comparator.comparing(p -> {
            var byName = p.getName() != null ? distance.apply(search, p.getName()) : 1000;
            var byAddress = p.getAddress() != null ? distance.apply(search, p.getAddress()) : 1000;
            return Math.min(byName, byAddress);
        });
    }

}
