package com.trip.server.overpass.query;

public class Node extends Set {

    private Node() {
        super("node");
    }

    public static Node withIds(Long... ids) {
        return new Node().ids(ids);
    }

    public static Node withArea() {
        return new Node().area();
    }

    public static Node withTags(String... tags) {
        return new Node().tags(tags);
    }

    @Override
    public Node ids(Long... ids) {
        super.ids(ids);
        return this;
    }

    @Override
    public Node area() {
        super.area();
        return this;
    }

    @Override
    public Node tags(String... tags) {
        super.tags(tags);
        return this;
    }

}
