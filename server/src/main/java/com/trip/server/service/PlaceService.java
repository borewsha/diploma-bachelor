package com.trip.server.service;

import com.trip.server.database.entity.Place;
import com.trip.server.database.repository.PlaceRepository;
import com.trip.server.exception.NotFoundException;
import com.trip.server.util.PageUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

    private final com.trip.server.overpass.repository.PlaceRepository overpassPlaceRepository;

    private final NominatimService nominatimService;

    private final ModelMapper modelMapper;

    public Page<Place> getBuildings(String city, @Nullable String search, Pageable pageable) {
        var buildings = search == null || search.isEmpty()
                ? overpassPlaceRepository.findBuildings(city, pageable)
                : overpassPlaceRepository.findBuildingsByAddressLike(city, search, pageable);
        geocodeBuildings(buildings.getContent());

        return toPageOfDatabasePlaces(buildings);
    }

    private void geocodeBuildings(List<com.trip.server.overpass.entity.Place> buildings) {
        var lookupResponse = nominatimService.lookupWays(buildings);

        buildings.forEach(b -> Optional.ofNullable(lookupResponse.get(b.getOsmId()))
                .ifPresent(a -> {
                    b.setLat(a.getLatitude());
                    b.setLon(a.getLongitude());
                })
        );
    }

    private Page<Place> toPageOfDatabasePlaces(Page<com.trip.server.overpass.entity.Place> overpassPlaces) {
        var osmIds = overpassPlaces.stream()
                .map(com.trip.server.overpass.entity.Place::getOsmId)
                .toList();
        var places = placeRepository.findByOsmIdIn(osmIds).stream()
                .collect(Collectors.toMap(Place::getOsmId, Function.identity()));
        var databasePlaces = overpassPlaces.stream()
                .map(p -> modelMapper.map(p, Place.class))
                .peek(p -> Optional.ofNullable(places.get(p.getOsmId()))
                        .ifPresent(db -> p.setId(db.getId()))
                )
                .toList();

        return PageUtil.mapContent(overpassPlaces, databasePlaces);
    }

    private static NotFoundException getNotFoundException() {
        return new NotFoundException("Место не найдено");
    }

}
