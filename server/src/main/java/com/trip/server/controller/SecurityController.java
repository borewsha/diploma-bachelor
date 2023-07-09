package com.trip.server.controller;

import javax.validation.*;

import com.trip.server.dto.error.ApiErrorDto;
import com.trip.server.dto.error.InvalidFieldsDto;
import com.trip.server.dto.CreatedDto;
import com.trip.server.dto.security.JwtDto;
import com.trip.server.dto.security.LoginDto;
import com.trip.server.dto.security.RefreshTokenDto;
import com.trip.server.dto.security.RegistrationDto;
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

@Validated
@AllArgsConstructor
@RestController
@Tag(name = "Security")
public class SecurityController extends ApiController {

    private final UserService userService;

    private final UserCredentialService userCredentialService;

    private final RefreshTokenService refreshTokenService;

    private final ModelMapper modelMapper;

    @Operation(summary = "Регистрация")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Пользователь успешно создан"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некоторые поля не прошли валидацию",
                    content = @Content(schema = @Schema(implementation = InvalidFieldsDto.class))
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Такое имя пользователя уже занято",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))
            )
    })
    @PostMapping("/registration")
    public ResponseEntity<CreatedDto> registration(@RequestBody @Valid RegistrationDto registrationDto) {
        var user = userService.register(registrationDto.getEmail(), registrationDto.getPassword(), registrationDto.getFullName());
        var userCreatedDto = new CreatedDto(user.getId());

        return new ResponseEntity<>(userCreatedDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Аутентификация")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Пользователь успешно аутентифицирован"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некоторые поля не прошли валидацию",
                    content = @Content(schema = @Schema(implementation = InvalidFieldsDto.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Неверный логин или пароль",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))
            )
    })
    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginDto loginDto) {
        var jwt = userCredentialService.generateJwt(loginDto.getEmail(), loginDto.getPassword());
        var jwtDto = modelMapper.map(jwt, JwtDto.class);
        return ResponseEntity.ok(jwtDto);
    }

    @Operation(summary = "Обновление access и refresh токенов")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Access и refresh токены успешно обновлены"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некоторые поля не прошли валидацию",
                    content = @Content(schema = @Schema(implementation = InvalidFieldsDto.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Невалидный refresh токен",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))
            )
    })
    @PostMapping("/refresh")
    public ResponseEntity<JwtDto> refresh(@Valid @RequestBody RefreshTokenDto refreshTokenDto) {
        var jwt = refreshTokenService.updateTokens(refreshTokenDto.getRefreshToken());
        var jwtDto = modelMapper.map(jwt, JwtDto.class);
        return ResponseEntity.ok(jwtDto);
    }

}

