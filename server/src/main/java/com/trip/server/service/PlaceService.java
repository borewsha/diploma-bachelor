package com.trip.server.service;

import com.trip.server.database.entity.Place;
import com.trip.server.database.repository.PlaceRepository;
import com.trip.server.exception.NotFoundException;
import com.trip.server.overpass.model.GeoFilters;
import com.trip.server.util.PageUtil;
import com.trip.server.util.TextUtil;
import fr.dudie.nominatim.model.Element;
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
public class PlaceService {

    private final PlaceRepository placeRepository;

    private final com.trip.server.overpass.repository.PlaceRepository overpassPlaceRepository;

    private final NominatimService nominatimService;

    private final ModelMapper modelMapper;

    public Page<Place> getBuildings(String city, @Nullable String search, Pageable pageable) {
        var buildings = search == null || search.isEmpty()
                ? overpassPlaceRepository.findBuildings(city, pageable)
                : overpassPlaceRepository.findBuildingsWithSearch(city, search, pageable);
        geocodeBuildings(buildings.getContent());

        return toPageOfDatabasePlaces(buildings);
    }

    public List<Place> getTourism(String city, @Nullable String search, GeoFilters geoFilters) {
        var tourism = search == null || search.isEmpty()
                ? overpassPlaceRepository.findTourism(city, geoFilters)
                : overpassPlaceRepository.findTourismWithSearch(city, search, geoFilters);
        geocodeTourism(tourism);

        return toListOfDatabasePlaces(tourism);
    }

    private void geocodeBuildings(List<com.trip.server.overpass.entity.Place> buildings) {
        var lookupResponse = nominatimService.lookupByObjects(buildings);

        buildings.forEach(b -> Optional.ofNullable(lookupResponse.get(b.getOsmId()))
                .ifPresent(a -> {
                    b.setLat(a.getLatitude());
                    b.setLon(a.getLongitude());
                })
        );
    }

    private void geocodeTourism(List<com.trip.server.overpass.entity.Place> tourism) {
        var lookupResponse = nominatimService.lookupByObjects(tourism);

        tourism.forEach(t -> Optional.ofNullable(lookupResponse.get(t.getOsmId()))
                .ifPresent(a -> {
                    Arrays.stream(a.getAddressElements())
                            .filter(e -> TextUtil.containsCyrillic(e.getValue()))
                            .filter(e -> e.getKey().equals("road") || e.getKey().equals("city_district"))
                            .findFirst()
                            .map(Element::getValue)
                            .ifPresent(t::setAddress);
                    t.setLat(a.getLatitude());
                    t.setLon(a.getLongitude());
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

    private List<Place> toListOfDatabasePlaces(List<com.trip.server.overpass.entity.Place> overpassPlaces) {
        var osmIds = overpassPlaces.stream()
                .map(com.trip.server.overpass.entity.Place::getOsmId)
                .toList();
        var places = placeRepository.findByOsmIdIn(osmIds).stream()
                .collect(Collectors.toMap(Place::getOsmId, Function.identity()));

        return overpassPlaces.stream()
                .map(p -> modelMapper.map(p, Place.class))
                .peek(p -> Optional.ofNullable(places.get(p.getOsmId()))
                        .ifPresent(db -> {
                            p.setId(db.getId());
                            p.setImage(db.getImage());
                        })
                )
                .toList();
    }

    private static NotFoundException getNotFoundException() {
        return new NotFoundException("Место не найдено");
    }

}
