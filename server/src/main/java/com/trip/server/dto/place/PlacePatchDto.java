package com.trip.server.dto.place;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Schema(description = "Изменение места")
public class PlacePatchDto {

    @Schema(
            description = "ID изображения",
            example = "6",
            nullable = true
    )
    @Nullable
    private Long imageId;

}
