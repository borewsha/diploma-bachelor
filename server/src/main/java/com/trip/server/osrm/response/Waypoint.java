package com.trip.server.osrm.response;

import lombok.Data;

import java.util.List;

@Data
public class Waypoint {

    private String name;

    private List<Float> location;

    private Double distance;

    private String hint;

}
