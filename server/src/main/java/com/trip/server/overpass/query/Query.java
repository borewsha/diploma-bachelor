package com.trip.server.overpass.query;

import com.trip.server.model.OsmType;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Query {

    private final List<String> defaultAreaTags;
    private final Settings settings = new Settings();
    private Area area = null;
    private final Body body = new Body();

    public Query(List<String> defaultAreaTags, OutputFormat format) {
        this.defaultAreaTags = defaultAreaTags;
        settings.setOutputFormat(format);
    }

    public Query id(String id) {
        var idAsNumber = Long.parseLong(id.substring(1));

        if (id.startsWith(OsmType.NODE.getPrefix())) {
            body.addSet(Node.withIds(idAsNumber));
        } else if (id.startsWith(OsmType.WAY.getPrefix())) {
            body.addSet(Way.withIds(idAsNumber));
        } else {
            body.addSet(Relation.withIds(idAsNumber));
        }

        return this;
    }

    public Query boundingBox(Double lat0, Double lon0, Double lat1, Double lon1) {
        settings.setBoundingBox(new BoundingBox(lat0, lon0, lat1, lon1));
        return this;
    }

    public Query boundingBox(@Nullable List<Double> bbox) {
        Optional.ofNullable(bbox)
                .map(bb -> new BoundingBox(bb.get(0), bb.get(1), bb.get(2), bb.get(3)))
                .ifPresent(settings::setBoundingBox);
        return this;
    }

    public Query defaultArea() {
        area = Area.withTags(defaultAreaTags.toArray(String[]::new));
        return this;
    }

    public Query area(Area area) {
        this.area = area;
        return this;
    }

    public Query set(Set set) {
        body.addSet(set);
        return this;
    }

    public String out() {
        return out(Out.DEFAULT);
    }

    /**
     * Own object type, Own object ID
     */
    public String outIds() {
        return out(Out.IDS);
    }

    /**
     * For nodes: Own object type, Own object ID, Own coordinates<br>
     * For ways: Own object type, Own object ID, IDs of member nodes<br>
     * For relations: Own object type, Own object ID, ID, type, role of members
     */
    public String outSkel() {
        return out(Out.SKEL);
    }

    /**
     * For nodes: Own object type, Own object ID, Own coordinates, Own tags<br>
     * For ways: Own object type, Own object ID, IDs of member nodes, Own tags<br>
     * For relations: Own object type, Own object ID, ID, type, role of members, Own tags
     */
    public String outBody() {
        return out(Out.BODY);
    }

    /**
     * Own object type, Own object ID, Own tags
     */
    public String outTags() {
        return out(Out.TAGS);
    }

    /**
     * For nodes: Own object type, Own object ID, Own coordinates, Own tags, Timestamp, VersionChangeset, User, User ID<br>
     * For ways: Own object type, Own object ID, IDs of member nodes, Own tags, Timestamp, VersionChangeset, User, User ID<br>
     * For relations: Own object type, Own object ID, ID, type, role of members, Own tags, Timestamp, VersionChangeset, User, User ID
     */
    public String outMeta() {
        return out(Out.META);
    }

    private String out(Out out) {
        return Stream.of(settings, area, body, out)
                .filter(Objects::nonNull)
                .map(String::valueOf)
                .filter(s -> !s.isBlank())
                .map(s -> s + ";")
                .collect(Collectors.joining());
    }

}
