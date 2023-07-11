package com.trip.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class RouteInfo {

    private Double distance;

    private Double duration;

    private RouteType type;

}
