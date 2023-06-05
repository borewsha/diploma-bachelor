package com.trip.server.overpass.query;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Set {

    protected final String type;

    protected String filter = null;

    protected List<Tag> tags = Collections.emptyList();

    protected Set ids(Long... ids) {
        filter = "id:" + Arrays.stream(ids)
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        return this;
    }

    protected Set area() {
        filter = "area.a";
        return this;
    }

    protected Set tags(String... tags) {
        this.tags = Arrays.stream(tags)
                .map(Tag::new)
                .toList();
        return this;
    }

    @Override
    public String toString() {
        return type +
                tags.stream().map(String::valueOf).collect(Collectors.joining()) +
                (filter == null ? "" : "(" + filter + ")");
    }
}
