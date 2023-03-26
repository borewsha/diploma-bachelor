package com.trip.server.dto;

import io.swagger.v3.oas.annotations.media.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сообщение об ошибке во время обработки запроса")
public class ApiErrorDto {

    @Schema(
        description = "Описание ошибки"
    )
    private String message;

}
