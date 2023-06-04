package com.trip.server.overpass.repository;

import com.trip.server.overpass.entity.City;
import com.trip.server.overpass.model.Element;
import com.trip.server.overpass.query.Node;
import com.trip.server.overpass.query.QueryBuilder;
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
@Component("overpassCityRepository")
public class CityRepository {

    private final OverpassMapDataApi overpassMapDataApi;

    private final JsonResponseReader jsonResponseReader;

    private final ModelMapper modelMapper;

    private final QueryBuilder queryBuilder;

    private final LevenshteinDistance distance;

    private final Comparator<City> populationComparator = Comparator.comparing(c -> -c.getPopulation());

    public Optional<City> findById(String id) {
        var query = queryBuilder.json()
                .id(id)
                .out();
        var response = getResponse(query);

        return response.stream()
                .map(e -> modelMapper.map(e, City.class))
                .findFirst();
    }

    public Page<City> findTop(Pageable pageable) {
        var content = streamCities()
                .sorted(populationComparator)
                .toList();

        return PageUtil.paginate(content, pageable);
    }

    public Page<City> findWithSearch(String search, Pageable pageable) {
        var similarityComparator = getSimilarityComparator(search);
        var content = streamCities()
                .sorted(similarityComparator)
                .toList();

        return PageUtil.paginate(content, pageable);
    }

    private List<Element> getResponse(String query) {
        log.debug("Overpass query: {}", query);
        return overpassMapDataApi.query(query, jsonResponseReader);
    }

    private Stream<City> streamCities() {
        var request = queryBuilder.json()
                .defaultArea()
                .set(Node.withTags("place~'city|town'").area())
                .out();

        return getResponse(request).stream()
                .map(e -> modelMapper.map(e, City.class));
    }

    private Comparator<City> getSimilarityComparator(String search) {
        return Comparator.comparing(c -> {
            var byName = c.getName() != null ? distance.apply(search, c.getName()) : 1000;
            var byRegion = c.getRegion() != null ? distance.apply(search, c.getRegion()) : 1000;
            return Math.min(byName, byRegion);
        });
    }

}
