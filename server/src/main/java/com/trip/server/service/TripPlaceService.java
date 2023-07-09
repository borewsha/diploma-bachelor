package com.trip.server.service;

import com.trip.server.database.entity.Trip;
import com.trip.server.database.repository.TripPlaceRepository;
import com.trip.server.exception.NotFoundException;
import com.trip.server.model.TripCreationModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@AllArgsConstructor
public class TripPlaceService {

    private final TripPlaceRepository tripPlaceRepository;

    private final OsrmService osrmService;

    public void makeTripRoutes(Trip trip, TripCreationModel tripCreationModel) {
        var days = DAYS.between(trip.getStartsAt(), trip.getEndsAt());
        if (tripCreationModel.getPlacesToVisit().size() <= days) {
            distributeLinearly(trip, tripCreationModel);
        }
    }

    private void distributeLinearly(Trip trip, TripCreationModel tripCreationModel) {

    }

    private static NotFoundException getNotFoundException() {
        return new NotFoundException("Место путешествия не найдено");
    }

}
