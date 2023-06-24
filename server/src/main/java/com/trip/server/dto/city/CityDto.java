package com.trip.server.dto.city;

import com.trip.server.model.Identifiable;
import com.trip.server.model.OsmIdentifiable;
import io.swagger.v3.oas.annotations.media.*;
import lombok.*;
import org.springframework.lang.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Schema(description = "Город")
public class CityDto implements Identifiable {

    @Schema(
        description = "ID в базе данных приложения",
        example = "1"
    )
    private Long id;

    @Schema(
            description = "ID изображения в базе данных",
            example = "2",
            nullable = true
    )
    @Nullable
    private Long imageId;

    @Schema(
            description = "Название",
            example = "Владивосток"
    )
    private String name;

    @Schema(
            description = "Регион",
            example = "Приморский край"
    )
    private String region;

    @Schema(
            description = "Широта",
            example = "43.1150678"
    )
    private Double lat;

    @Schema(
            description = "Долгота",
            example = "131.8855768"
    )
    private Double lon;

}
