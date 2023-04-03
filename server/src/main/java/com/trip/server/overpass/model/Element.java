package com.trip.server.overpass.model;

import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Data
public class Element {

    private Long id;

    private Type type;

    @Nullable
    private Double lat;

    @Nullable
    private Double lon;

    private List<Long> nodes = Collections.emptyList();

    private Map<String, String> tags = Collections.emptyMap();

}
