package com.trip.server.model;

import com.trip.server.database.entity.Place;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class PlaceGroup {

    private LocalDate date;

    private List<Route> routes;

    public static PlaceGroup empty() {
        return new PlaceGroup(null, new ArrayList<>());
    }

    public Double getDistance() {
        return routes.stream().mapToDouble(r -> r.getInfo().getDistance()).sum();
    }

    public Double getRouteDuration() {
        return routes.stream().mapToDouble(r -> r.getInfo().getDuration()).sum();
    }

    public Double getDestinationSpentTime() {
        return routes.stream().mapToDouble(Route::getDestinationSpentTime).sum();
    }

    public Double getSpentTime() {
        return getRouteDuration() + getDestinationSpentTime();
    }

    public Boolean isEmpty() {
        return routes.isEmpty();
    }

    @Nullable
    public Place getSource() {
        return routes.isEmpty() ? null : routes.get(0).getSource();
    }

    @Nullable
    public Place getDestination() {
        return routes.isEmpty() ? null : routes.get(routes.size() - 1).getDestination();
    }

    public void appendRoute(Route route) {
        routes.add(route);
    }

}
