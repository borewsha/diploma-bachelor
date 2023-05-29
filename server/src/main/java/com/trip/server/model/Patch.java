package com.trip.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface Patch {

    @JsonIgnore
    Boolean isEmpty();

}
