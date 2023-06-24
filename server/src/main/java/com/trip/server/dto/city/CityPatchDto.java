package com.trip.server.dto.city;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Schema(description = "Изменение города")
public class CityPatchDto {

    @Schema(
            description = "ID изображения",
            example = "2",
            nullable = true
    )
    @Nullable
    private Long imageId;

}
