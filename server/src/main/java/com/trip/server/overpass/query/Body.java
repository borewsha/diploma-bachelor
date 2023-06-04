package com.trip.server.overpass.query;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class Body {

    private final List<Set> sets = new ArrayList<>();

    public void addSet(Set set) {
        sets.add(set);
    }

    @Override
    public String toString() {
        if (sets.isEmpty()) {
            return "";
        }
        if (sets.size() == 1) {
            return sets.get(0).toString();
        }
        return "(" + sets.stream().map(s -> s + ";").collect(Collectors.joining()) + ")";
    }
}
