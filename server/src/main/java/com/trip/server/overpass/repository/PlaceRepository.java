package com.trip.server.overpass.repository;

import com.trip.server.database.enumeration.PlaceType;
import com.trip.server.mapper.PlaceMapper;
import com.trip.server.overpass.Connector;
import com.trip.server.overpass.entity.Place;
import com.trip.server.overpass.model.Element;
import com.trip.server.overpass.query.*;
import com.trip.server.util.PageUtil;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component("overpassPlaceRepository")
public class PlaceRepository extends Repository {

    private final ModelMapper modelMapper;

    private final QueryBuilder queryBuilder;

    private final static Map<PlaceType, Predicate<Element>> TYPES = PlaceMapper.TYPES.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

    public PlaceRepository(Connector connector, ModelMapper modelMapper, QueryBuilder queryBuilder) {
        super(connector);
        this.modelMapper = modelMapper;
        this.queryBuilder = queryBuilder;
    }

    public Page<Place> findByCity(
            String city,
            @Nullable String search,
            @Nullable Set<PlaceType> types,
            Pageable pageable
    ) {
        var request = queryBuilder.json()
                .area(Area.withTags("place~'city|town'", "name='" + city + "'"))
                .set(Way.withArea().tags("building", "'addr:street'", "'addr:housenumber'"))
                .set(NodeWay.withArea().tags("~'^(name|description)$'~'.'"))
                .out();
        var response = filterByType(getResponse(request, search), types);

        var pagedElements = PageUtil.paginate(response, pageable);
        var places = pagedElements.stream()
                .map(e -> modelMapper.map(e, Place.class))
                .toList();

        return PageUtil.mapContent(pagedElements, places);
    }

    private List<Element> filterByType(List<Element> elements, @Nullable Set<PlaceType> types) {
        return elements.stream()
                .filter(
                        types == null || types.isEmpty()
                                ? e -> TYPES.values().stream().anyMatch(v -> v.test(e))
                                : e -> types.stream().anyMatch(t -> TYPES.getOrDefault(t, x -> false).test(e))
                )
                .toList();
    }

    @Override
    protected Stream<String> getFilteredFields(Element e) {
        return Stream.of(PlaceMapper.getName(e), PlaceMapper.getAddress(e));
    }

}
