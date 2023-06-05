package com.trip.server.overpass.query;

import lombok.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Area {

    private String filter = null;

    private List<Tag> tags = Collections.emptyList();

    public static Area withIds(Long... ids) {
        return new Area().ids(ids);
    }

    public static Area withTags(String... tags) {
        return new Area().tags(tags);
    }

    public Area ids(Long... ids) {
        filter = "id:" + Arrays.stream(ids)
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        return this;
    }

    public Area tags(String... tags) {
        this.tags = Arrays.stream(tags)
                .map(Tag::new)
                .toList();
        return this;
    }

    @Override
    public String toString() {
        return "area" +
                tags.stream().map(String::valueOf).collect(Collectors.joining()) +
                (filter == null ? "" : "(" + filter + ")") +
                "->.a";
    }

}
