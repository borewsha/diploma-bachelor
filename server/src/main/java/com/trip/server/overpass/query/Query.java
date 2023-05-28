package com.trip.server.overpass.query;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Query {

    private final String type;

    private final String defaultAreaTags;

    private String area = null;

    private String body = null;

    public Query(String type, String defaultAreaTags) {
        this.type = asTag("out:" + type);
        this.defaultAreaTags = defaultAreaTags;
    }

    private String asTag(String tag) {
        return "[" + tag + "]";
    }

    private String asTags(String... tags) {
        return Stream.of(tags)
                .map(this::asTag)
                .collect(Collectors.joining());
    }

    private String asLine(String... parts) {
        return join("", (Object[]) parts) + ";";
    }

    private String join(String delimiter, Object... objects) {
        return Stream.of(objects)
                .map(Object::toString)
                .collect(Collectors.joining(delimiter));
    }

    private String withArea(String query) {
        return query.formatted(area != null ? "(area)" : "");
    }

    public Query defaultArea() {
        area = asLine("area", defaultAreaTags);
        return this;
    }

    public Query area(Long... ids) {
        area = asLine("area(id: ", join(", ", (Object[]) ids), ")");
        return this;
    }

    public Query area(String... tags) {
        area = asLine("area", asTags(tags));
        return this;
    }

    public Query node(Long... ids) {
        body = asLine("node(id: ", join(", ", (Object[]) ids), ")");
        return this;
    }

    public Query node(String... tags) {
        body = asLine("node", asTags(tags), "%s");
        return this;
    }

    public Query way(Long... ids) {
        body = asLine("way(id: ", join(", ", (Object[]) ids), ")");
        return this;
    }

    public Query way(String... tags) {
        body = asLine("way", asTags(tags), "%s");
        return this;
    }

    public String out() {
        return asLine(type) +
                (area != null ? area : "") +
                (body != null ? withArea(body) : "") +
                asLine("out");
    }

}
