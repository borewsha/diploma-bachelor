package com.trip.server.mapper;

import com.trip.server.database.entity.Trip;
import com.trip.server.dto.trip.TripCreationDto;
import com.trip.server.dto.trip.TripDto;
import com.trip.server.model.TripCreationModel;
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
                .map(TimeUtil::getFormattedLocalDate)
                .toList();
    }

}
