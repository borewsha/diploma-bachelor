package com.trip.server.dto.security;

import javax.validation.constraints.*;

import io.swagger.v3.oas.annotations.media.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Access и refresh токены")
public class JwtDto {

    @Schema(
        description = "Access токен, зашифрованный алгоритмом HS256",
        example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiZXhwIjoxNjczMDk2MTAxLCJpYXQiOjE2NzMwOTI1MDEsInJvbGUiOiJST0xFX" +
                  "1VTRVIifQ.NSo89EzF8vofHNDXrXc08378sjxiq2FvtVyNSqBNu-rvIO_6EhcF5PtBm6k7LV-nBPtGhLmU64Ij_GDdNao8Hw"
    )
    @NotEmpty(message = "Access токен обязателен")
    private String accessToken;

    @Schema(
        description = "Refresh токен, зашифрованный алгоритмом HS256",
        example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiZXhwIjoxNjc1Njg0NTAxfQ.8Tja7GoCJiosY6xWwGvcVWeDD1utanm6DTy_UM" +
                  "EUDe_lVIL3BzD3lB_HVexHhfe9ulda9st02VDRaGvRaADq_w"
    )
    @NotEmpty(message = "Refresh токен обязателен")
    private String refreshToken;

}
