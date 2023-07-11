package com.trip.server.mapper;

import com.trip.server.database.entity.Trip;
import com.trip.server.dto.city.CityDto;
import com.trip.server.dto.place.PlaceDto;
import com.trip.server.dto.trip.*;
import com.trip.server.model.Day;
import com.trip.server.model.TripCreationModel;
import com.trip.server.model.TripExtended;
import com.trip.server.model.Way;
import com.trip.server.util.TimeUtil;
import org.modelmapper.ModelMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Component
public class TripMapper implements Mapper {

    public void init(ModelMapper modelMapper) {
        modelMapper.createTypeMap(Trip.class, TripDto.class)
                .setConverter(mappingContext -> {
                    var trip = mappingContext.getSource();

                    return new TripDto(
                            trip.getId(),
                            trip.getUser().getId(),
                            trip.getCity().getId(),
                            getAccommodationId(trip),
                            getCityImageId(trip),
                            trip.getCity().getName(),
                            getDates(trip.getStartsAt(), trip.getEndsAt())
                    );
                });

        modelMapper.createTypeMap(TripCreationDto.class, TripCreationModel.class)
                .setConverter(mappingContext -> {
                    var tripCreationDto = mappingContext.getSource();

                    return new TripCreationModel(
                            null,
                            null,
                            null,
                            getStartsAt(tripCreationDto.getDates()),
                            getEndsAt(tripCreationDto.getDates()),
                            Collections.emptyList()
                    );
                });

        modelMapper.createTypeMap(TripExtended.class, TripExtendedDto.class)
                .setConverter(mappingContext -> {
                    var tripExtended = mappingContext.getSource();

                    return new TripExtendedDto(
                            tripExtended.getId(),
                            tripExtended.getUser().getId(),
                            modelMapper.map(tripExtended.getCity(), CityDto.class),
                            modelMapper.map(tripExtended.getAccommodation(), PlaceDto.class),
                            getDates(tripExtended.getStartsAt(), tripExtended.getEndsAt()),
                            tripExtended.getDays().stream()
                                    .map(d -> modelMapper.map(d, DayDto.class))
                                    .toList()
                    );
                });

        modelMapper.createTypeMap(Day.class, DayDto.class)
                .setConverter(mappingContext -> {
                    var day = mappingContext.getSource();

                    return new DayDto(
                            TimeUtil.getFormattedLocalDate(day.getDate().plusDays(1)),
                            day.getPlaces().stream()
                                    .map(p -> modelMapper.map(p, PlaceDto.class))
                                    .toList(),
                            day.getWays().stream()
                                    .map(p -> modelMapper.map(p, WayDto.class))
                                    .toList()
                    );
                });

        modelMapper.createTypeMap(Way.class, WayDto.class)
                .setConverter(mappingContext -> {
                    var way = mappingContext.getSource();

                    return new WayDto(
                            way.getType().name().toLowerCase(),
                            way.getPoints()
                    );
                });
    }

    @Nullable
    public static Long getAccommodationId(Trip trip) {
        return trip.getAccommodation() != null ? trip.getAccommodation().getId() : null;
    }

    @Nullable
    public static Long getCityImageId(Trip trip) {
        return trip.getCity().getImage() != null ? trip.getCity().getImage().getId() : null;
    }

    public static LocalDate getStartsAt(List<LocalDate> dates) {
        return dates.get(0);
    }

    public static LocalDate getEndsAt(List<LocalDate> dates) {
        return dates.get(1);
    }

    public static List<String> getDates(LocalDate startsAt, LocalDate endsAt) {
        return Stream.of(startsAt, endsAt)
                .map(d -> d.plusDays(1))
                .map(TimeUtil::getFormattedLocalDate)
                .toList();
    }

}
