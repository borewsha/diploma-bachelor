package com.trip.server.dto.user;

import com.trip.server.model.Identifiable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UserDto implements Identifiable {

    @Schema(
            description = "ID пользователя",
            example = "6"
    )
    private Long id;

    @Schema(
            description = "Роль пользователя",
            example = "ROLE_USER"
    )
    private String role;

    @Schema(
            description = "Время регистрации пользователя с таймзоной",
            example = "2023-05-12T12:41:47.435013Z"
    )
    private String registeredAt;

    @Schema(
            description = "Полное имя пользователя",
            example = "Иванов Иван Иванович"
    )
    private String fullName;

}
