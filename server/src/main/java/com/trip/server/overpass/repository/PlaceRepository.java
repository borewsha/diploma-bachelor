package com.trip.server.overpass.repository;

import com.trip.server.mapper.PlaceMapper;
import com.trip.server.overpass.Connector;
import com.trip.server.overpass.entity.Place;
import com.trip.server.overpass.model.Element;
import com.trip.server.overpass.model.GeoFilters;
import com.trip.server.overpass.query.*;
import com.trip.server.util.PageUtil;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Stream;

@Component("overpassPlaceRepository")
public class PlaceRepository extends Repository {

    private final ModelMapper modelMapper;

    private final QueryBuilder queryBuilder;

    public PlaceRepository(Connector connector, ModelMapper modelMapper, QueryBuilder queryBuilder) {
        super(connector);
        this.modelMapper = modelMapper;
        this.queryBuilder = queryBuilder;
    }

    public Page<Place> findByCity(String city, @Nullable String search, Pageable pageable) {
        var request = queryBuilder.json()
                .area(Area.withTags("place~'city|town'", "name='" + city + "'"))
                .set(Way.withArea().tags("building", "'addr:street'", "'addr:housenumber'"))
                .set(NodeWay.withArea().tags(
                        "tourism",
                        "tourism!~'^(hotel|hostel|information|apartment|guest_house)$'",
                        "~'^(name|description)$'~'.'"
                ))
                .out();
        var pagedElements = PageUtil.paginate(getResponse(request, search), pageable);
        var places = pagedElements.stream()
                .map(e -> modelMapper.map(e, Place.class))
                .toList();

        return PageUtil.mapContent(pagedElements, places);
    }

    public Page<Place> findBuildingsByCity(String city, @Nullable String search, Pageable pageable) {
        var request = queryBuilder.json()
                .area(Area.withTags("place~'city|town'", "name='" + city + "'"))
                .set(Way.withArea().tags("building", "'addr:street'", "'addr:housenumber'"))
                .out();
        var pagedElements = PageUtil.paginate(getResponse(request, search), pageable);
        var buildings = pagedElements.stream()
                .map(e -> modelMapper.map(e, Place.class))
                .toList();

        return PageUtil.mapContent(pagedElements, buildings);
    }

    public List<Place> findTourismByCity(String city, @Nullable String search, GeoFilters filters) {
        var request = queryBuilder.json()
                .boundingBox(filters.getBbox())
                .area(Area.withTags("place~'city|town'", "name='" + city + "'"))
                .set(NodeWay.withArea().tags(
                        "tourism",
                        "tourism!~'^(hotel|hostel|information|apartment|guest_house)$'",
                        "~'^(name|description)$'~'.'"
                ))
                .out();

        return getResponse(request, search).stream()
                .map(e -> modelMapper.map(e, Place.class))
                .toList();
    }

    @Override
    protected Stream<String> getFilteredFields(Element e) {
        return Stream.of(PlaceMapper.getName(e), PlaceMapper.getAddress(e));
    }

}
