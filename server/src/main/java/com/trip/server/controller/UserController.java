package com.trip.server.controller;

import com.trip.server.dto.PageDto;
import com.trip.server.dto.PageParamsDto;
import com.trip.server.dto.SortParamsDto;
import com.trip.server.dto.error.ApiErrorDto;
import com.trip.server.dto.trip.TripDto;
import com.trip.server.dto.user.UserDto;
import com.trip.server.service.*;
import com.trip.server.util.PageUtil;
import com.trip.server.util.SortUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.*;
import lombok.*;
import org.modelmapper.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@AllArgsConstructor
@RestController
@Tag(name = "Users")
@RequestMapping("/api/users")
public class UserController extends ApiController {

    private final ModelMapper modelMapper;

    private final UserService userService;

    private final TripService tripService;

    @Operation(summary = "Аутентифицированный пользователь")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Данные об аутентифицированном пользователе"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Невалидный access токен",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))
            )
    })
    @GetMapping("/self")
    public ResponseEntity<UserDto> getSelf() {
        var user = userService.getCurrentUser();
        var userDto = modelMapper.map(user, UserDto.class);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @Operation(summary = "Все пользователи в системе")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Данные обо всех пользователях"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Доступ запрещен - только администраторы имеют право просматривать всех пользователей",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))
            )
    })
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<PageDto<UserDto>> getAll(
            @Valid PageParamsDto pageParamsDto,
            @Valid SortParamsDto sortParamsDto
    ) {
        var pageRequest = PageUtil.request(pageParamsDto);
        var sortRequest = SortUtil.request(sortParamsDto);
        var page = userService.getAll(pageRequest.withSort(sortRequest));
        var pageDto = PageUtil.toDto(modelMapper, page, UserDto.class);

        return new ResponseEntity<>(pageDto, HttpStatus.OK);
    }

    @Operation(summary = "Поиск пользователя")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Данные о пользователе"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Доступ запрещен - недостаточно прав на просмотр пользователя",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь не найден",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))
            )
    })
    @PreAuthorize("hasAuthority('ROLE_ADMIN') || userAccess.hasRightToView(#id)")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id) {
        var user = userService.getById(id);
        var userDto = modelMapper.map(user, UserDto.class);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @Operation(summary = "Путешествия пользователя")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Путешествия пользователя успешно отданы"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Доступ запрещен - недостаточно прав на просмотр путешествий пользователя",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь не найден",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))
            )
    })
    @PreAuthorize("hasAuthority('ROLE_ADMIN') || userAccess.hasRightToView(#id)")
    @GetMapping("/{id}/trips")
    public ResponseEntity<PageDto<TripDto>> getTrips(
            @PathVariable Long id,
            @Valid PageParamsDto pageParamsDto,
            @Valid SortParamsDto sortParamsDto
    ) {
        var pageRequest = PageUtil.request(pageParamsDto);
        var sortRequest = SortUtil.request(sortParamsDto);
        var user = userService.getById(id);
        var page = tripService.getByUser(user, pageRequest.withSort(sortRequest));
        var pageDto = PageUtil.toDto(modelMapper, page, TripDto.class);

        return new ResponseEntity<>(pageDto, HttpStatus.OK);
    }

}
