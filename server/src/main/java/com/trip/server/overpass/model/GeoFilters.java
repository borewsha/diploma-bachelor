package com.trip.server.overpass.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeoFilters {

    private List<Double> bbox;

    private Integer radius;

}
