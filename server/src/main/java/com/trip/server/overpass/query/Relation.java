package com.trip.server.overpass.query;

public class Relation extends Set {

    private Relation() {
        super("rel");
    }

    public static Relation withIds(Long... ids) {
        return new Relation().ids(ids);
    }

    public static Relation withArea() {
        return new Relation().area();
    }

    public static Relation withTags(String... tags) {
        return new Relation().tags(tags);
    }

    @Override
    public Relation ids(Long... ids) {
        super.ids(ids);
        return this;
    }

    @Override
    public Relation area() {
        super.area();
        return this;
    }

    @Override
    public Relation tags(String... tags) {
        super.tags(tags);
        return this;
    }

}
