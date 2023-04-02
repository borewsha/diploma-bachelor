package com.trip.server.overpass.model;

import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Data
public class Element {

    private Long id;

    private Type type;

    private List<Long> nodes = Collections.emptyList();

    private Map<String, String> tags = Collections.emptyMap();

}
