package com.trip.server.service;

import com.trip.server.database.entity.City;
import com.trip.server.database.entity.Place;
import com.trip.server.database.repository.PlaceRepository;
import com.trip.server.exception.NotFoundException;
import com.trip.server.model.PlacePatchModel;
import com.trip.server.overpass.model.GeoFilters;
import com.trip.server.util.PageUtil;
import com.trip.server.util.TextUtil;
import fr.dudie.nominatim.model.Element;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

    private final com.trip.server.overpass.repository.PlaceRepository overpassPlaceRepository;

    private final NominatimService nominatimService;

    private final ModelMapper modelMapper;

    public Place getById(Long id) {
        return placeRepository.findById(id)
                .orElseThrow(PlaceService::getNotFoundException);
    }

    public List<Place> getByIds(List<Long> ids) {
        var places = placeRepository.findByIdIn(ids);

        if (ids.size() != places.size()) {
            var placesIds = places.stream()
                    .map(Place::getId)
                    .toList();
            var unknownIds = ids.stream()
                    .filter(placesIds::contains)
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));
            throw new NotFoundException("Места не найдены: " + unknownIds);
        }

        return places;
    }

    public Page<Place> getByCity(City city, @Nullable String search, Pageable pageable) {
        var pagedOverpassPlaces = overpassPlaceRepository.findByCity(city.getName(), search, pageable);
        var pagedDatabasePlaces = toPagedDatabasePlaces(pagedOverpassPlaces, city);

        var unsavedPlaces = pagedDatabasePlaces.getContent().stream()
                .filter(p -> p.getId() == null)
                .toList();
        geocode(unsavedPlaces);
        placeRepository.saveAll(unsavedPlaces);

        return pagedDatabasePlaces;
    }

    public Page<Place> getBuildingsByCity(City city, @Nullable String search, Pageable pageable) {
        var pagedOverpassBuildings = overpassPlaceRepository.findBuildingsByCity(city.getName(), search, pageable);
        var pagedDatabaseBuildings = toPagedDatabasePlaces(pagedOverpassBuildings, city);

        var unsavedBuildings = pagedDatabaseBuildings.getContent().stream()
                .filter(b -> b.getId() == null)
                .toList();
        geocode(unsavedBuildings);
        placeRepository.saveAll(unsavedBuildings);

        return pagedDatabaseBuildings;
    }

    public List<Place> getTourismByCity(City city, @Nullable String search, GeoFilters geoFilters) {
        var listedOverpassTourism = overpassPlaceRepository.findTourismByCity(city.getName(), search, geoFilters);
        var listedDatabaseTourism = toListedDatabasePlaces(listedOverpassTourism, city);

        var unsavedTourism = listedDatabaseTourism.stream()
                .filter(t -> t.getId() == null)
                .toList();
        geocode(unsavedTourism);
        placeRepository.saveAll(unsavedTourism);

        return listedDatabaseTourism;
    }

    public void patch(Long id, PlacePatchModel placePatchModel) {
        if (placePatchModel.isEmpty()) {
            return;
        }
        var place = getById(id);

        if (placePatchModel.isImageSet()) {
            place.setImage(placePatchModel.getImage());
        }

        placeRepository.save(place);
    }

    private void geocode(List<Place> places) {
        var lookupResponse = nominatimService.lookupByOsmIdentifiable(places);

        places.forEach(p -> Optional.ofNullable(lookupResponse.get(p.getOsmId()))
                .ifPresent(a -> {
                    if (p.getAddress() == null) {
                        Arrays.stream(a.getAddressElements())
                                .filter(e -> TextUtil.containsCyrillic(e.getValue()))
                                .filter(e -> e.getKey().equals("road") || e.getKey().equals("city_district"))
                                .findFirst()
                                .map(Element::getValue)
                                .ifPresent(p::setAddress);
                    }
                    if (p.getLat() == null || p.getLon() == null) {
                        p.setLat(a.getLatitude());
                        p.setLon(a.getLongitude());
                    }
                })
        );
    }

    private List<Place> toListedDatabasePlaces(List<com.trip.server.overpass.entity.Place> overpassPlaces, City city) {
        var osmIds = overpassPlaces.stream()
                .map(com.trip.server.overpass.entity.Place::getOsmId)
                .toList();
        var places = placeRepository.findByOsmIdIn(osmIds).stream()
                .collect(Collectors.toMap(Place::getOsmId, Function.identity()));

        return overpassPlaces.stream()
                .map(p -> places.getOrDefault(p.getOsmId(), modelMapper.map(p, Place.class)))
                .map(c -> c.getId() == null ? c.setCity(city) : c)
                .toList();
    }

    private Page<Place> toPagedDatabasePlaces(Page<com.trip.server.overpass.entity.Place> overpassPlaces, City city) {
        var databasePlaces = toListedDatabasePlaces(overpassPlaces.getContent(), city);
        return PageUtil.mapContent(overpassPlaces, databasePlaces);
    }

    private static NotFoundException getNotFoundException() {
        return new NotFoundException("Место не найдено");
    }

}
