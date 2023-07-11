package com.trip.server.service;

import com.trip.server.model.Coordinatable;
import com.trip.server.osrm.OsrmClient;
import com.trip.server.osrm.request.Waypoint;
import com.trip.server.osrm.response.RouteResponse;
import com.trip.server.osrm.response.TableResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OsrmService {

    private OsrmClient osrmClientCar;

    private OsrmClient osrmClientFoot;

    public RouteResponse getCarRoute(List<? extends Coordinatable> coordinates) {
        var waypoints = coordinates.stream()
                .map(c -> new Waypoint(c.getLat(), c.getLon()))
                .toList();

        return osrmClientCar.route(waypoints);
    }

    public RouteResponse getFootRoute(List<? extends Coordinatable> coordinates) {
        var waypoints = coordinates.stream()
                .map(c -> new Waypoint(c.getLat(), c.getLon()))
                .toList();

        return osrmClientFoot.route(waypoints);
    }

    public TableResponse getCarTable(List<? extends Coordinatable> coordinates) {
        var waypoints = coordinates.stream()
                .map(c -> new Waypoint(c.getLat(), c.getLon()))
                .toList();

        return osrmClientCar.table(waypoints);
    }

    public TableResponse getFootTable(List<? extends Coordinatable> coordinates) {
        var waypoints = coordinates.stream()
                .map(c -> new Waypoint(c.getLat(), c.getLon()))
                .toList();

        return osrmClientFoot.table(waypoints);
    }

}
