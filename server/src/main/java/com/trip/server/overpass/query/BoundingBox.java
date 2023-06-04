package com.trip.server.overpass.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.stream.Stream;

@Getter
@Setter
@AllArgsConstructor
public class BoundingBox {

    private Double lat0;
    private Double lon0;
    private Double lat1;
    private Double lon1;

    @Override
    public String toString() {
        var coordinates = Stream.of(lat0, lon0, lat1, lon1)
                .map(String::valueOf)
                .toList();

        return "[bbox:" + String.join(",", coordinates) + "]";
    }

}
