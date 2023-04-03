package com.trip.server.dto;

import io.swagger.v3.oas.annotations.media.*;
import lombok.*;
import org.springframework.lang.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Schema(description = "Город")
public class CityDto {

    @Schema(
        description = "ID",
        example = "23565445"
    )
    private Long id;

    @Schema(
            description = "Название",
            example = "Владивосток"
    )
    private String name;

    @Schema(
            description = "Регион",
            example = "Приморский край"
    )
    @Nullable
    private String region;

    @Schema(
            description = "Широта",
            example = "43.1150678"
    )
    @Nullable
    private Double lat;

    @Schema(
            description = "Долгота",
            example = "131.8855768"
    )
    @Nullable
    private Double lon;

}
