package com.trip.server.overpass.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Settings {

    private OutputFormat outputFormat;

    private BoundingBox boundingBox;

    @Override
    public String toString() {
        return Stream.of(outputFormat, boundingBox)
                .filter(Objects::nonNull)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

}
