package com.trip.server.model;

import com.trip.server.database.entity.Place;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Route {

    private Place source;

    private Place destination;

    private Double destinationSpentTime;

    private RouteInfo info;

    public Double getSpentTime() {
        return info.getDuration() + destinationSpentTime;
    }

}
