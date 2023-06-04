package com.trip.server.overpass.query;

public enum OutputFormat {

    JSON;

    @Override
    public String toString() {
        return "[out:" + this.name().toLowerCase() + "]";
    }

}
