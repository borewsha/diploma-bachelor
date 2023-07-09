package com.trip.server.osrm.response;

import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.List;

@Data
public class Intersection {

    private List<Double> location;

    private List<Integer> bearings;

    @Nullable
    private List<String> classes;

    private List<Boolean> entry;

    @Nullable
    private Integer in;

    private Integer out;

    @Nullable
    private List<Lane> lanes;

}
