package com.trip.server.controller;

import javax.validation.*;

import com.trip.server.dto.*;
import com.trip.server.service.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;
import lombok.*;
import org.modelmapper.*;
import org.springframework.http.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Tag(name = "Security")
@Validated
public class SecurityController extends ApiController {

    private final UserService userService;

    private final UserCredentialService userCredentialService;

    private final RefreshTokenService refreshTokenService;

    private final ModelMapper modelMapper;

    @Operation(summary = "Регистрация")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "CREATED"
        ),
        @ApiResponse(
            responseCode = "422",
            description = "Такое имя пользователя уже занято",
            content = @Content(schema = @Schema(implementation = ApiErrorDto.class))
        )
    })
    @PostMapping("/registration")
    public ResponseEntity<CreatedDto> registration(
        @Valid @RequestBody RegistrationDto registrationDto
    ) {
        var userId = userService.register(registrationDto.getUsername(), registrationDto.getPassword());
        var userCreatedDto = new CreatedDto(userId);

        return new ResponseEntity<>(userCreatedDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Аутентификация")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "ОК"
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Неверный логин или пароль",
            content = @Content(schema = @Schema(implementation = ApiErrorDto.class))
        )
    })
    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(
        @Valid @RequestBody LoginDto loginDto
    ) {
        var jwt = userCredentialService.generateJwt(loginDto.getUsername(), loginDto.getPassword());
        var jwtDto = modelMapper.map(jwt, JwtDto.class);
        return ResponseEntity.ok(jwtDto);
    }

    @Operation(summary = "Обновление access и refresh токенов")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "ОК"
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Невалидный refresh токен",
            content = @Content(schema = @Schema(implementation = ApiErrorDto.class))
        )
    })
    @PostMapping("/refresh")
    public ResponseEntity<JwtDto> updateTokens(
        @Valid @RequestBody RefreshTokenDto refreshTokenDto
    ) {
        var jwt = refreshTokenService.updateTokens(refreshTokenDto.getRefreshToken());
        var jwtDto = modelMapper.map(jwt, JwtDto.class);
        return ResponseEntity.ok(jwtDto);
    }

}

