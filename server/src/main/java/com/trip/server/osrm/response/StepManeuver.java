package com.trip.server.osrm.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.List;

@Data
public class StepManeuver {

    @Nullable
    private List<Double> location;

    @Nullable
    @JsonProperty("bearing_before")
    private Integer bearingBefore;

    @Nullable
    @JsonProperty("bearing_after")
    private Integer bearingAfter;

    private String type;

    private String modifier;

    private Integer exit;

}
