package com.trip.server.osrm.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Route {

    private Double distance;

    private Double duration;

    private String geometry;

    private Double weight;

    @JsonProperty("weight_name")
    private String weightName;

    private List<RouteLeg> legs;

}
