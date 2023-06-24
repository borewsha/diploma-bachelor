package com.trip.server.dto.place;

import com.trip.server.model.Identifiable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Schema(description = "Место")
public class PlaceDto implements Identifiable {

    @Schema(
            description = "ID в базе данных приложения",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "ID города, в котором находится данное место",
            example = "67"
    )
    private Long cityId;

    @Schema(
            description = "ID изображения в базе данных",
            example = "2",
            nullable = true
    )
    @Nullable
    private Long imageId;

    @Schema(
            description = "Тип",
            example = "building"
    )
    private String type;

    @Schema(
            description = "Название",
            example = "Океан",
            nullable = true
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
