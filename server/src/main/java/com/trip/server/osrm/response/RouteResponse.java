package com.trip.server.osrm.response;

import lombok.Data;

import java.util.List;

@Data
public class RouteResponse {

    private String code;

    private List<Route> routes;

    private List<Waypoint> waypoints;

}
