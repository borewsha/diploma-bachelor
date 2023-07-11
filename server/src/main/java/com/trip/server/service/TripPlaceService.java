package com.trip.server.service;

import com.trip.server.database.entity.Place;
import com.trip.server.database.entity.Trip;
import com.trip.server.database.entity.TripPlace;
import com.trip.server.database.enumeration.PlaceType;
import com.trip.server.database.repository.TripPlaceRepository;
import com.trip.server.exception.NotFoundException;
import com.trip.server.exception.UnprocessableEntityException;
import com.trip.server.model.*;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.iterators.PermutationIterator;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@AllArgsConstructor
public class TripPlaceService {

    private final TripPlaceRepository tripPlaceRepository;

    private final OsrmService osrmService;

    private static final Comparator<PlaceGroup> SPENT_TIME_DESCENDING_COMPARATOR = Comparator.comparing(pg -> -pg.getSpentTime());

    private static final Double MAX_FOOT_DISTANCE = 2000.0;

    private static final Double HOUR = 3600.0;

    private static final Double DESIRED_SPENT_TIME = HOUR * 6;

    private static final Map<PlaceType, Double> PLACE_TYPE_DURATION = Map.of(
            PlaceType.ATTRACTION, HOUR,
            PlaceType.CINEMA, HOUR * 2,
            PlaceType.HOTEL, HOUR / 2,
            PlaceType.HOUSE, HOUR,
            PlaceType.MONUMENT, HOUR / 4,
            PlaceType.MUSEUM, HOUR * 2,
            PlaceType.THEATRE, HOUR * 2,
            PlaceType.VIEWPOINT, HOUR,
            PlaceType.UNKNOWN, HOUR
    );

    public List<TripPlace> getByTrip(Trip trip) {
        var sort = Sort.by("date", "order").ascending();
        return tripPlaceRepository.findByTrip(trip, sort);
    }

    public void makeTripRoutes(Trip trip, TripCreationModel tripCreationModel) {
        var groups = splitInGroups(trip, tripCreationModel).stream()
                .sorted(SPENT_TIME_DESCENDING_COMPARATOR)
                .toList();

        for (var i = 0; i < groups.size(); ++i) {
            var group = groups.get(i);
            group.setDate(trip.getStartsAt().plusDays(i));


            for (var j = 0; j < group.getRoutes().size(); ++j) {
                var route = group.getRoutes().get(j);

                var tripPlace = TripPlace.builder()
                        .trip(trip)
                        .place(route.getSource())
                        .date(group.getDate())
                        .order(j)
                        .type(com.trip.server.database.enumeration.RouteType.valueOf(route.getInfo().getType().name()))
                        .build();
                tripPlaceRepository.save(tripPlace);
            }

            var tripPlace = TripPlace.builder()
                    .trip(trip)
                    .place(trip.getAccommodation())
                    .date(group.getDate())
                    .order(group.getRoutes().size())
                    .type(com.trip.server.database.enumeration.RouteType.FOOT)
                    .build();
            tripPlaceRepository.save(tripPlace);
        }
    }

    private Long getDaysNumber(Trip trip) {
        return DAYS.between(trip.getStartsAt(), trip.getEndsAt());
    }

    private Map<Place, Map<Place, RouteInfo>> getRouteMatrix(Place accommodation, List<Place> placesToVisit) {
        var places = ListUtils.union(List.of(accommodation), placesToVisit);
        var footTable = osrmService.getFootTable(places);
        var carTable = osrmService.getCarTable(places);

        return places.stream()
                .collect(Collectors.toMap(Function.identity(), p1 -> places.stream()
                        .collect(Collectors.toMap(Function.identity(), p2 -> {
                            var i = places.indexOf(p1);
                            var j = places.indexOf(p2);

                            var footDistance = footTable.getDistances().get(i).get(j);
                            var footDuration = footTable.getDurations().get(i).get(j);
                            var carDistance = carTable.getDistances().get(i).get(j);
                            var carDuration = carTable.getDurations().get(i).get(j);

                            return footDistance != null && (footDistance <= MAX_FOOT_DISTANCE || carDistance == null)
                                    ? new RouteInfo(footDistance, footDuration, RouteType.FOOT)
                                    : new RouteInfo(carDistance, carDuration, RouteType.CAR);
                        }))
                ));
    }

    private List<PlaceGroup> splitInGroups(Trip trip, TripCreationModel tripCreationModel) {
        var routeMatrix = getRouteMatrix(trip.getAccommodation(), tripCreationModel.getPlacesToVisit());
        var permutations = new PermutationIterator<>(tripCreationModel.getPlacesToVisit());

        List<PlaceGroup> bestGroups = null;

        while (permutations.hasNext()) {
            var permutation = permutations.next();

            var groups = new ArrayList<PlaceGroup>();
            var currentGroup = PlaceGroup.empty();

            for (var place : permutation) {
                var source = currentGroup.isEmpty() ? trip.getAccommodation() : currentGroup.getDestination();
                var routeToPlace = new Route(
                        source,
                        place,
                        PLACE_TYPE_DURATION.get(place.getType()),
                        routeMatrix.get(source).get(place)
                );
                var routeToAccommodation = new Route(
                        place,
                        trip.getAccommodation(),
                        0.0,
                        routeMatrix.get(place).get(trip.getAccommodation())
                );

                var spentTime = routeToPlace.getSpentTime() + routeToAccommodation.getSpentTime();

                if (currentGroup.getSpentTime() + spentTime > DESIRED_SPENT_TIME) {
                    var routeFromSourceToAccommodation = new Route(
                            source,
                            trip.getAccommodation(),
                            0.0,
                            routeMatrix.get(source).get(trip.getAccommodation())
                    );
                    currentGroup.appendRoute(routeFromSourceToAccommodation);

                    groups.add(currentGroup);
                    currentGroup = PlaceGroup.empty();
                }

                if (currentGroup.getSpentTime() + spentTime <= DESIRED_SPENT_TIME) {
                    currentGroup.appendRoute(routeToPlace);
                } else {
                    break;
                }
            }

            if (!currentGroup.isEmpty()) {
                var routeFromLastPlaceToAccommodation = new Route(
                        currentGroup.getDestination(),
                        trip.getAccommodation(),
                        0.0,
                        routeMatrix.get(currentGroup.getDestination()).get(trip.getAccommodation())
                );
                currentGroup.appendRoute(routeFromLastPlaceToAccommodation);
                groups.add(currentGroup);

                if (bestGroups == null) {
                    bestGroups = groups;
                } else {
                    var groupsSpentTime = groups.stream().mapToDouble(PlaceGroup::getSpentTime).sum();
                    var bestGroupsSpentTime = bestGroups.stream().mapToDouble(PlaceGroup::getSpentTime).sum();

                    if (groupsSpentTime < bestGroupsSpentTime) {
                        bestGroups = groups;
                    }
                }
            }
        }

        if (bestGroups == null) {
            throw new UnprocessableEntityException("Невозможно найти оптимальный маршрут");
        }

        return bestGroups;
    }

    private static NotFoundException getNotFoundException() {
        return new NotFoundException("Место путешествия не найдено");
    }

}
