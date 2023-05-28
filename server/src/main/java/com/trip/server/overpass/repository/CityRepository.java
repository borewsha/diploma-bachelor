package com.trip.server.overpass.repository;

import com.trip.server.overpass.entity.City;
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

import java.util.Comparator;
import java.util.Optional;

@Component("overpassCityRepository")
@RequiredArgsConstructor
public class CityRepository {

    private final OverpassMapDataApi overpassMapDataApi;

    private final JsonResponseReader jsonResponseReader;

    private final ModelMapper modelMapper;

    private final QueryBuilder queryBuilder;

    private final Comparator<Element> populationComparator = Comparator.comparing(
            e -> -Integer.parseInt(e.getTags().getOrDefault("population", "0"))
    );

    public Optional<City> findById(Long id) {
        var query = queryBuilder.json()
                .node(id)
                .out();
        var response = overpassMapDataApi.query(query, jsonResponseReader);

        return response.stream()
                .map(e -> modelMapper.map(e, City.class))
                .findFirst();
    }

    public Page<City> findTop(Pageable pageable) {
        var query = queryBuilder.json()
                .defaultArea()
                .node("place~'city|town'")
                .out();
        var response = overpassMapDataApi.query(query, jsonResponseReader);
        var content = response.stream()
                .sorted(populationComparator)
                .map(e -> modelMapper.map(e, City.class))
                .toList();

        return PageUtil.paginate(content, pageable);
    }

    public Page<City> findByNameLike(String pattern, Pageable pageable) {
        var query = queryBuilder.json()
                .defaultArea()
                .node("place~'city|town'", "name~'.*" + pattern + ".*',i")
                .out();
        var response = overpassMapDataApi.query(query, jsonResponseReader);
        var content = response.stream()
                .sorted(populationComparator)
                .map(e -> modelMapper.map(e, City.class))
                .toList();

        return PageUtil.paginate(content, pageable);
    }

}
