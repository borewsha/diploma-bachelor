package com.trip.server.dto.security;

import javax.validation.constraints.*;

import io.swagger.v3.oas.annotations.media.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные для регистрации пользователя")
public class RegistrationDto {

    @Schema(
            description = "ФИО пользователя",
            example = "Иванов Иван"
    )
    @NotEmpty(message = "ФИО обязательно")
    private String fullName;

    @Schema(
        description = "Почта пользователя",
        example = "ivanov@mail.ru"
    )
    @NotEmpty(message = "Почта пользователя обязательна")
    private String email;

    @Schema(
        description = "Пароль пользователя",
        example = "password"
    )
    @NotEmpty(message = "Пароль обязателен")
    private String password;

}
