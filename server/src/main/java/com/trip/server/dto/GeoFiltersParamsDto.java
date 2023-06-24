package com.trip.server.dto;

import com.trip.server.validator.BoundingBox;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Positive;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Параметры фильтрации геоданных")
public class GeoFiltersParamsDto {

    @Schema(
            description = "Границы карты",
            example = "43.02008,131.76280,43.19103,131.98511",
            nullable = true
    )
    @Nullable
    @BoundingBox
    private List<Double> bbox = null;

    @Schema(
            description = "Радиус вокруг относительно некоторой точки (в км)",
            example = "5",
            nullable = true
    )
    @Nullable
    @Positive
    private Integer radius = null;

}
