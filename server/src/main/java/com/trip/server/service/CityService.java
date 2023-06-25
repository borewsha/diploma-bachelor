package com.trip.server.service;

import com.trip.server.database.entity.City;
import com.trip.server.exception.NotFoundException;
import com.trip.server.database.repository.CityRepository;
import com.trip.server.model.CityPatchModel;
import com.trip.server.util.PageUtil;
import fr.dudie.nominatim.model.Element;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    private final com.trip.server.overpass.repository.CityRepository overpassCityRepository;

    private final NominatimService nominatimService;

    private final ModelMapper modelMapper;

    public City getById(Long id) {
        return cityRepository.findById(id)
                .orElseThrow(CityService::getNotFoundException);
    }

    @Cacheable(cacheNames = "citiesCache", key = "{#search, #pageable}")
    public Page<City> getAll(@Nullable String search, Pageable pageable) {
        var pagedOverpassCities = overpassCityRepository.findAll(search, pageable);
        var pagedDatabaseCities = toPagedDatabaseCities(pagedOverpassCities);

        var unsavedCities = pagedDatabaseCities.getContent().stream()
                .filter(c -> c.getId() == null)
                .toList();
        geocode(unsavedCities);
        cityRepository.saveAll(unsavedCities);

        return pagedDatabaseCities;
    }

    @CacheEvict(cacheNames = "citiesCache", allEntries = true)
    public void patch(Long id, CityPatchModel cityPatchModel) {
        if (cityPatchModel.isEmpty()) {
            return;
        }
        var city = getById(id);

        if (cityPatchModel.isImageSet()) {
            city.setImage(cityPatchModel.getImage());
        }

        cityRepository.save(city);
    }

    private void geocode(List<City> cities) {
        var lookupResponse = nominatimService.lookupByOsmIdentifiable(cities);

        cities.forEach(c -> Optional.ofNullable(lookupResponse.get(c.getOsmId()))
                .flatMap(a -> Arrays.stream(a.getAddressElements())
                        .filter(e -> e.getKey().equals("state"))
                        .findFirst()
                )
                .map(Element::getValue)
                .ifPresent(c::setRegion)
        );
    }

    private List<City> toListedDatabaseCities(List<com.trip.server.overpass.entity.City> overpassCities) {
        var osmIds = overpassCities.stream()
                .map(com.trip.server.overpass.entity.City::getOsmId)
                .toList();
        var cities = cityRepository.findByOsmIdIn(osmIds).stream()
                .collect(Collectors.toMap(City::getOsmId, Function.identity()));

        return overpassCities.stream()
                .map(c -> cities.getOrDefault(c.getOsmId(), modelMapper.map(c, City.class)))
                .toList();
    }

    private Page<City> toPagedDatabaseCities(Page<com.trip.server.overpass.entity.City> overpassCities) {
        var databaseCities = toListedDatabaseCities(overpassCities.getContent());
        return PageUtil.mapContent(overpassCities, databaseCities);
    }

    private static NotFoundException getNotFoundException() {
        return new NotFoundException("Город не найден");
    }

}
