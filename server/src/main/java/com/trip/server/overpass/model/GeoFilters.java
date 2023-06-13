package com.trip.server.overpass.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeoFilters {

    @Nullable
    private List<Double> bbox;

    @Nullable
    private Integer radius;

}
