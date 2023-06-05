package com.trip.server.overpass.query;

public class Way extends Set {

    private Way() {
        super("way");
    }

    public static Way withIds(Long... ids) {
        return new Way().ids(ids);
    }

    public static Way withArea() {
        return new Way().area();
    }

    public static Way withTags(String... tags) {
        return new Way().tags(tags);
    }

    @Override
    public Way ids(Long... ids) {
        super.ids(ids);
        return this;
    }

    @Override
    public Way area() {
        super.area();
        return this;
    }

    @Override
    public Way tags(String... tags) {
        super.tags(tags);
        return this;
    }

}
