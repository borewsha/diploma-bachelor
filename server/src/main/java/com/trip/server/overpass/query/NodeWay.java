package com.trip.server.overpass.query;

public class NodeWay extends Set {

    private NodeWay() {
        super("nw");
    }

    public static NodeWay withIds(Long... ids) {
        return new NodeWay().ids(ids);
    }

    public static NodeWay withArea() {
        return new NodeWay().area();
    }

    public static NodeWay withTags(String... tags) {
        return new NodeWay().tags(tags);
    }

    @Override
    public NodeWay ids(Long... ids) {
        super.ids(ids);
        return this;
    }

    @Override
    public NodeWay area() {
        super.area();
        return this;
    }

    @Override
    public NodeWay tags(String... tags) {
        super.tags(tags);
        return this;
    }

}
