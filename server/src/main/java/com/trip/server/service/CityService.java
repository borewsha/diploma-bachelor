package com.trip.server.service;

import com.trip.server.exception.NotFoundException;
import com.trip.server.overpass.entity.City;
import com.trip.server.overpass.repository.CityRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@AllArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    public Page<City> get(@Nullable String search, Pageable pageable) {
        return search == null || search.isEmpty()
                ? cityRepository.findTop(pageable)
                : cityRepository.findByNameLike(search, pageable);
    }

    private static NotFoundException getNotFoundException() {
        return new NotFoundException("Город не найден");
    }

}
