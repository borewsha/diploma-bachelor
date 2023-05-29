package com.trip.server.service;

import com.trip.server.database.entity.City;
import com.trip.server.exception.NotFoundException;
import com.trip.server.database.repository.CityRepository;
import com.trip.server.model.CityPatch;
import com.trip.server.model.IdentificationProvider;
import com.trip.server.util.PageUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    private final com.trip.server.overpass.repository.CityRepository overpassCityRepository;

    private final ImageService imageService;

    private final NominatimService nominatimService;

    private final ModelMapper modelMapper;

    public City getById(Long id, @Nullable IdentificationProvider provider) {
        if (provider == IdentificationProvider.OSM) {
            return cityRepository.findByOsmId(id)
                    .orElseThrow(CityService::getNotFoundException);
        }

        return cityRepository.findById(id)
                .orElseThrow(CityService::getNotFoundException);
    }

    public Page<City> getAll(@Nullable String search, Pageable pageable) {
        var overpassCities = search == null || search.isEmpty()
                ? overpassCityRepository.findTop(pageable)
                : overpassCityRepository.findByNameLike(search, pageable);
        geocode(overpassCities.getContent());

        return toPageOfDatabaseCities(overpassCities);
    }

    public void patch(Long id, @Nullable IdentificationProvider provider, CityPatch patch) {
        if (patch.isEmpty()) {
            return;
        }
        var city = getEntity(id, provider);

        if (patch.isImageIdSet()) {
            var image = imageService.getById(patch.getImageId());
            city.setImage(image);
        }

        cityRepository.save(city);
    }

    private City getEntity(Long id, @Nullable IdentificationProvider provider) {
        try {
            return getById(id, provider);
        } catch (NotFoundException e) {
            if (provider == IdentificationProvider.OSM) {
                return overpassCityRepository.findById(id).stream()
                        .peek(c -> geocode(List.of(c)))
                        .map(c -> modelMapper.map(c, City.class))
                        .findFirst()
                        .orElseThrow(CityService::getNotFoundException);
            }
        }

        throw getNotFoundException();
    }

    private void geocode(List<com.trip.server.overpass.entity.City> cities) {
        var lookupResponse = nominatimService.lookupNodes(cities);

        cities.forEach(c -> Optional.ofNullable(lookupResponse.get(c.getOsmId()))
                .flatMap(a -> Arrays.stream(a.getAddressElements())
                        .filter(e -> e.getKey().equals("state"))
                        .findFirst()
                )
                .ifPresent(e -> c.setRegion(e.getValue()))
        );
    }

    private Page<City> toPageOfDatabaseCities(Page<com.trip.server.overpass.entity.City> overpassCities) {
        var osmIds = overpassCities.stream()
                .map(com.trip.server.overpass.entity.City::getOsmId)
                .toList();
        var cities = cityRepository.findByOsmIdIn(osmIds).stream()
                .collect(Collectors.toMap(City::getOsmId, Function.identity()));
        var databaseCities = overpassCities.stream()
                .map(c -> modelMapper.map(c, City.class))
                .peek(c -> Optional.ofNullable(cities.get(c.getOsmId()))
                        .ifPresent(db -> {
                            c.setId(db.getId());
                            c.setImage(db.getImage());
                        })
                )
                .toList();

        return PageUtil.mapContent(overpassCities, databaseCities);
    }

    private static NotFoundException getNotFoundException() {
        return new NotFoundException("Город не найден");
    }

}
