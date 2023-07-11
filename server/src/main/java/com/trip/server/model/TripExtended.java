package com.trip.server.model;

import com.trip.server.database.entity.City;
import com.trip.server.database.entity.Place;
import com.trip.server.database.entity.User;
import com.trip.server.dto.city.CityDto;
import com.trip.server.dto.place.PlaceDto;
import com.trip.server.dto.trip.DayDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;


@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class TripExtended {

    private Long id;

    private User user;

    private City city;

    private Place accommodation;

    private LocalDate startsAt;

    private LocalDate endsAt;

    private List<Day> days;

}
