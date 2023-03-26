package com.trip.server.dto;

import io.swagger.v3.oas.annotations.media.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Schema(description = "Успешный ответ после создания сущности")
public class CreatedDto {

    @Schema(
        description = "ID сущности",
        example = "5"
    )
    private Long id;

}
