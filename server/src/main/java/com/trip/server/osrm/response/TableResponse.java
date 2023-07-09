package com.trip.server.osrm.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TableResponse {

    private String code;

    private List<List<Double>> durations;

    private List<List<Double>> distances;

    private List<Waypoint> sources;

    private List<Waypoint> destinations;

    @JsonProperty("fallback_speed_cells")
    private List<List<Integer>> fallbackSpeedCells;

}
