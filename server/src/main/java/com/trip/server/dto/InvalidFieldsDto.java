package com.trip.server.dto;

import java.util.*;

import io.swagger.v3.oas.annotations.media.*;
import lombok.*;

@Data
@AllArgsConstructor
@Schema(description = "Ошибки, произошедшие при валидации полей")
public class InvalidFieldsDto {

    @Schema(
        description = "Описание ошибки"
    )
    private String message;

    @Schema(
        description = "Список ошибок с описание"
    )
    private List<InvalidFieldDto> errors;

}