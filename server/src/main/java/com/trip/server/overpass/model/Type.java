package com.trip.server.overpass.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum Type {

    @JsonProperty("area")
    AREA,

    @JsonProperty("node")
    NODE,

    @JsonProperty("relation")
    RELATION,

    @JsonProperty("way")
    WAY

}
