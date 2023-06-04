package com.trip.server.overpass.query;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Tag {

    private String value;

    @Override
    public String toString() {
        return "[" + value + "]";
    }

}
