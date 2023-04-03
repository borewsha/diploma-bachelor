package com.trip.server.overpass.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.lang.Nullable;

import static lombok.AccessLevel.PROTECTED;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class City {

    private Long id;

    private String name;

    @Nullable
    private String region;

    @Nullable
    private Double lat;

    @Nullable
    private Double lon;

}
