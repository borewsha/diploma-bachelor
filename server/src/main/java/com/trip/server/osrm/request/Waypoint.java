package com.trip.server.osrm.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Waypoint {

    private Double latitude;

    private Double longitude;

    @Override
    public String toString() {
        return longitude + "," + latitude;
    }

}
