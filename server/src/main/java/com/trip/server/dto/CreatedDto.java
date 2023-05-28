package com.trip.server.dto;

import com.trip.server.model.Identifiable;
import io.swagger.v3.oas.annotations.media.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Schema(description = "Успешный ответ после создания сущности")
public class CreatedDto implements Identifiable {

    @Schema(
        description = "ID сущности",
        example = "5"
    )
    private Long id;

}
