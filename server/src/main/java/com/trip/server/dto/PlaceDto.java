package com.trip.server.dto;

import com.trip.server.model.Identifiable;
import com.trip.server.model.OsmIdentifiable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Schema(description = "Место")
public class PlaceDto implements Identifiable, OsmIdentifiable {

    @Schema(
            description = "ID в базе данных приложения",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "ID изображения в базе данных",
            example = "2"
    )
    @Nullable
    private Long imageId;

    @Schema(
            description = "ID в базе данных OSM",
            example = "23946365"
    )
    @Nullable
    private Long osmId;

    @Schema(
            description = "Название",
            example = "Океан"
    )
    @Nullable
    private String name;

    @Schema(
            description = "Улица",
            example = "Набережная улица, 3"
    )
    private String address;

    @Schema(
            description = "Широта",
            example = "43.1164693"
    )
    private Double lat;

    @Schema(
            description = "Долгота",
            example = "131.8780963"
    )
    private Double lon;

}
