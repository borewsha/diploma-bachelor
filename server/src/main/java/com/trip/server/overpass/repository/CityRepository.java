package com.trip.server.overpass.repository;

import com.trip.server.overpass.entity.City;
import com.trip.server.overpass.model.Element;
import com.trip.server.overpass.reader.JsonResponseReader;
import com.trip.server.util.PageUtil;
import de.westnordost.osmapi.overpass.OverpassMapDataApi;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class CityRepository {

    private final OverpassMapDataApi overpassMapDataApi;

    private final JsonResponseReader jsonResponseReader;

    private final ModelMapper modelMapper;

    private final Comparator<Element> populationComparator = Comparator.comparing(
            e -> -Integer.parseInt(e.getTags().getOrDefault("population", "0"))
    );

    public Page<City> findTop(Pageable pageable) {
        var query = JsonResponseReader.getQueryBuilder()
                .append("node[place~'city|town'];")
                .append("out;")
                .toString();
        var response = overpassMapDataApi.query(query, jsonResponseReader);
        var content = response.stream()
                .sorted(populationComparator)
                .map(e -> modelMapper.map(e, City.class))
                .toList();

        return PageUtil.paginate(content, pageable);
    }

    public Page<City> findByNameLike(String pattern, Pageable pageable) {
        var query = JsonResponseReader.getQueryBuilder()
                .append("node[place~'city|town']")
                .append("[name~'.*").append(pattern).append(".*',i];")
                .append("out;")
                .toString();
        var response = overpassMapDataApi.query(query, jsonResponseReader);
        var content = response.stream()
                .sorted(populationComparator)
                .map(e -> modelMapper.map(e, City.class))
                .toList();

        return PageUtil.paginate(content, pageable);
    }

}
