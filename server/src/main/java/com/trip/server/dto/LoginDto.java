package com.trip.server.dto;

import javax.validation.constraints.*;

import io.swagger.v3.oas.annotations.media.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные для входа в аккаунт")
public class LoginDto {

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
