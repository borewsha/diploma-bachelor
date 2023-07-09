package com.trip.server.osrm.response;

import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.List;

@Data
public class RouteLeg {

    private Double distance;

    private Double duration;

    private Double weight;

    @Nullable
    private String summary;

    private List<RouteStep> steps;

    private Annotation annotation;

}
