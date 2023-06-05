package com.trip.server.overpass.query;

public enum Out {

    IDS,
    SKEL,
    BODY,
    TAGS,
    META,
    DEFAULT;

    @Override
    public String toString() {
        return this == DEFAULT ? "out" : "out " + this.name().toLowerCase();
    }

}
