package com.trip.server.osrm.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.List;

@Data
public class RouteStep {

    private Double distance;

    private Double duration;

    private String geometry;

    private Double weight;

    private String name;

    @Nullable
    private Integer ref;

    @Nullable
    private String pronunciation;

    @Nullable
    private List<Object> destinations;

    @Nullable
    private List<Object> exits;

    private String mode;

    private StepManeuver maneuver;

    private List<Intersection> intersections;

    @JsonProperty("rotary_name")
    private String rotaryName;

    @JsonProperty("rotary_pronunciation")
    private String rotaryPronunciation;

    @JsonProperty("driving_side")
    private String drivingSide;

}
