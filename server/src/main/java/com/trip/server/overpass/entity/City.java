package com.trip.server.overpass.entity;

import com.trip.server.model.Coordinatable;
import com.trip.server.model.OsmIdentifiable;
import com.trip.server.model.OsmType;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.lang.Nullable;

import static lombok.AccessLevel.PROTECTED;

@Data
@Builder
@AllArgsConstructor
@Accessors(chain = true)
@NoArgsConstructor(access = PROTECTED)
public class City implements OsmIdentifiable, Coordinatable {

    private String osmId;

    private String name;

    @Nullable
    private String region;

    private Double lat;

    private Double lon;

    private Integer population;

}
