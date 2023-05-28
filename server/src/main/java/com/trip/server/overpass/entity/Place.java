package com.trip.server.overpass.entity;

import com.trip.server.model.OsmIdentifiable;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.lang.Nullable;

import static lombok.AccessLevel.PROTECTED;

@Data
@Builder
@AllArgsConstructor
@Accessors(chain = true)
@NoArgsConstructor(access = PROTECTED)
public class Place implements OsmIdentifiable {

    private Long osmId;

    private String type;

    @Nullable
    private String name;

    @Nullable
    private String address;

    @Nullable
    private Double lat;

    @Nullable
    private Double lon;

}
