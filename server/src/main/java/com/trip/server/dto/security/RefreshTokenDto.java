package com.trip.server.dto.security;

import io.swagger.v3.oas.annotations.media.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Schema(description = "Refresh токен для получения новых access и refresh токенов")
public class RefreshTokenDto {

    @Schema(
        description = "Refresh токен, зашифрованный алгоритмом HS256",
        example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiZXhwIjoxNjc1Njg0NTAxfQ.8Tja7GoCJiosY6xWwGvcVWeDD1utanm6DTy_UM" +
                  "EUDe_lVIL3BzD3lB_HVexHhfe9ulda9st02VDRaGvRaADq_w"
    )
    private String refreshToken;

}
