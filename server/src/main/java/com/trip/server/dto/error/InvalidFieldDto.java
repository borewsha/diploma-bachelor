package com.trip.server.dto.error;

import io.swagger.v3.oas.annotations.media.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Поле, не прошедшее валидацию, и описание ошибки")
public class InvalidFieldDto {

    @Schema(
        description = "Название поля",
        example = "username"
    )
    private String field;

    @Schema(
        description = "Информация об ошибке",
        example = "Имя пользователя обязательно"
    )
    private String message;

}