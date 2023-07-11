package com.trip.server.service;

import com.trip.server.database.entity.Trip;
import com.trip.server.database.entity.TripPlace;
import com.trip.server.database.entity.User;
import com.trip.server.database.enumeration.RouteType;
import com.trip.server.database.repository.TripRepository;
import com.trip.server.exception.NotFoundException;
import com.trip.server.exception.UnprocessableEntityException;
import com.trip.server.model.Day;
import com.trip.server.model.TripCreationModel;
import com.trip.server.model.TripExtended;
import com.trip.server.model.Way;
import com.trip.server.osrm.response.Intersection;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TripService {

    private final TripRepository tripRepository;

    private final OsrmService osrmService;

    public Trip getById(Long id) {
        return tripRepository.findById(id)
                .orElseThrow(TripService::getNotFoundException);
    }

    public Page<Trip> getByUser(User user, Pageable pageable) {
        return tripRepository.findByUser(user, pageable);
    }

    public Trip create(TripCreationModel tripCreationModel) {
        var areCitiesMatch = tripCreationModel.getPlacesToVisit().stream()
                .allMatch(a -> a.getCity().equals(tripCreationModel.getCity()));

        if (!areCitiesMatch) {
            throw new UnprocessableEntityException("Не все места находятся в введенном городе");
        }

        var trip = Trip.builder()
                .user(tripCreationModel.getUser())
                .city(tripCreationModel.getCity())
                .accommodation(tripCreationModel.getAccommodation())
                .startsAt(tripCreationModel.getStartsAt())
                .endsAt(tripCreationModel.getEndsAt())
                .build();

        return tripRepository.save(trip);
    }

    public Page<Trip> getAll(Pageable pageable) {
        return tripRepository.findAll(pageable);
    }

    public TripExtended getExtendedModel(Trip trip, List<TripPlace> tripPlaces) {
        var tripExtended = TripExtended.builder()
                .id(trip.getId())
                .user(trip.getUser())
                .city(trip.getCity())
                .accommodation(trip.getAccommodation())
                .startsAt(trip.getStartsAt())
                .endsAt(trip.getEndsAt())
                .days(new ArrayList<>())
                .build();

        var days = tripPlaces.stream()
                .collect(Collectors.groupingBy(TripPlace::getDate));

        for (var entry : days.entrySet()) {
            var date = entry.getKey();
            var places = entry.getValue();

            var day = Day.builder()
                    .date(date)
                    .places(places.stream()
                            .map(TripPlace::getPlace)
                            .filter(p -> !p.equals(trip.getAccommodation()))
                            .toList()
                    )
                    .ways(new ArrayList<>())
                    .build();

            for (var i = 0; i < places.size() - 1; ++i) {
                var source = places.get(i);
                var destination = places.get(i + 1);

                var coordinates = List.of(source.getPlace(), destination.getPlace());
                var response = source.getType() == RouteType.FOOT
                        ? osrmService.getFootRoute(coordinates)
                        : osrmService.getCarRoute(coordinates);

                var way = Way.builder()
                        .type(com.trip.server.model.RouteType.valueOf(source.getType().name()))
                        .points(response.getRoutes().get(0).getLegs().get(0).getSteps().stream()
                                .flatMap(s -> s.getIntersections().stream().map(Intersection::getLocation))
                                .peek(Collections::reverse)
                                .toList()
                        )
                        .build();
                day.getWays().add(way);
            }

            tripExtended.getDays().add(day);
        }

        return tripExtended;
    }

    private static NotFoundException getNotFoundException() {
        return new NotFoundException("Путешествие не найдено");
    }

}
