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
        description = "Имя пользователя",
        example = "username"
    )
    @NotEmpty(message = "Имя пользователя обязательно")
    private String username;

    @Schema(
        description = "Пароль пользователя",
        example = "password"
    )
    @NotEmpty(message = "Пароль обязателен")
    private String password;

}
