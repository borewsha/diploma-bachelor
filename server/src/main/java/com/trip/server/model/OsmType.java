package com.trip.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum OsmType {

    @JsonProperty("area")
    AREA("A"),

    @JsonProperty("node")
    NODE("N"),

    @JsonProperty("relation")
    RELATION("R"),

    @JsonProperty("way")
    WAY("W");

    private final String prefix;

    OsmType(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefixedId(Long id) {
        return prefix + id;
    }

}
