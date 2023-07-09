package com.trip.server.model;

import com.trip.server.database.entity.City;
import com.trip.server.database.entity.Place;
import com.trip.server.database.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class TripCreationModel {

    @Nullable
    private User user;

    @Nullable
    private City city;

    @Nullable
    private Place accommodation;

    private LocalDate startsAt;

    private LocalDate endsAt;

    private List<Place> placesToVisit;

}
