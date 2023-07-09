package com.trip.server.osrm.response;

import lombok.Data;

import java.util.List;

@Data
public class Lane {

    private Boolean valid;

    private List<String> indications;

}
