package com.trip.server.overpass.repository;

import com.trip.server.mapper.CityMapper;
import com.trip.server.overpass.Connector;
import com.trip.server.overpass.entity.City;
import com.trip.server.overpass.model.Element;
import com.trip.server.overpass.query.Node;
import com.trip.server.overpass.query.QueryBuilder;
import com.trip.server.util.PageUtil;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.stream.Stream;

@Component("overpassCityRepository")
public class CityRepository extends Repository {

    private final ModelMapper modelMapper;

    private final QueryBuilder queryBuilder;

    private final Comparator<City> populationComparator = Comparator.comparing(c -> -c.getPopulation());

    public CityRepository(Connector connector, ModelMapper modelMapper, QueryBuilder queryBuilder) {
        super(connector);
        this.modelMapper = modelMapper;
        this.queryBuilder = queryBuilder;
    }

    public Page<City> findAll(@Nullable String search, Pageable pageable) {
        var request = queryBuilder.json()
                .defaultArea()
                .set(Node.withArea().tags("place~'city|town'"))
                .out();
        var pagedElements = PageUtil.paginate(getResponse(request, search), pageable);
        var cities = pagedElements.stream()
                .map(e -> modelMapper.map(e, City.class))
                .sorted(populationComparator)
                .toList();

        return PageUtil.mapContent(pagedElements, cities);
    }

    @Override
    protected Stream<String> getFilteredFields(Element e) {
        return Stream.of(CityMapper.getName(e), CityMapper.getRegion(e));
    }

}
