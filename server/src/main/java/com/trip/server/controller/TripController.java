package com.trip.server.controller;

import com.trip.server.dto.error.ApiErrorDto;
import com.trip.server.dto.trip.TripCreationDto;
import com.trip.server.dto.trip.TripDto;
import com.trip.server.dto.CreatedDto;
import com.trip.server.dto.PageDto;
import com.trip.server.dto.PageParamsDto;
import com.trip.server.dto.SortParamsDto;
import com.trip.server.dto.trip.TripExtendedDto;
import com.trip.server.model.TripCreationModel;
import com.trip.server.model.TripExtended;
import com.trip.server.service.*;
import com.trip.server.util.PageUtil;
import com.trip.server.util.SortUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Validated
@AllArgsConstructor
@RestController
@Tag(name = "Trips")
@RequestMapping("/api/trips")
public class TripController extends ApiController {

    private final ModelMapper modelMapper;

    private final TripService tripService;

    private final TripPlaceService tripPlaceService;

    private final UserService userService;

    private final CityService cityService;

    private final PlaceService placeService;

    @Operation(summary = "Список всех путешествий в системе")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список всех путешествий успешно отдан"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Доступ запрещен - только администраторы имеют доступ",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))
            )
    })
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<PageDto<TripDto>> getAll(
            @Valid PageParamsDto pageParamsDto,
            @Valid SortParamsDto sortParamsDto
    ) {
        var pageRequest = PageUtil.request(pageParamsDto);
        var sortRequest = SortUtil.request(sortParamsDto);
        var page = tripService.getAll(pageRequest.withSort(sortRequest));
        var pageDto = PageUtil.toDto(modelMapper, page, TripDto.class);

        return ResponseEntity.ok(pageDto);
    }

    @Operation(summary = "Поиск путешествия")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Путешествие найдено"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Доступ запрещен - нет прав на просмотр путешествия для другого пользователя",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Путешествие не найдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))
            )
    })
    @PreAuthorize("hasAuthority('ROLE_ADMIN') || @tripAccess.hasRightToView(#id)")
    @GetMapping("/{id}")
    public ResponseEntity<TripExtendedDto> getById(@PathVariable Long id) {
        var trip = tripService.getById(id);
        var tripPlaces = tripPlaceService.getByTrip(trip);

        var tripExtended = tripService.getExtendedModel(trip, tripPlaces);
        var tripExtendedDto = modelMapper.map(tripExtended, TripExtendedDto.class);

        return ResponseEntity.ok(tripExtendedDto);
    }


    @Operation(summary = "Создание путешествия")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Путешествие успешно создано"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Доступ запрещен - нет прав на создание путешествия для другого пользователя",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = """
                            Возможные ошибки:
                            - Пользователь не найден
                            - Город не найден
                            - Место не найдено
                            - Места не найдены: {id}, ...
                            """,
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))
            )
    })
    @PreAuthorize("hasAuthority('ROLE_ADMIN') || @tripAccess.hasRightToCreate(#tripCreationDto.getUserId())")
    @PostMapping
    public ResponseEntity<CreatedDto> create(@Valid @RequestBody TripCreationDto tripCreationDto) {
        var tripCreationModel = modelMapper.map(tripCreationDto, TripCreationModel.class)
                .setUser(userService.getById(tripCreationDto.getUserId()))
                .setCity(cityService.getById(tripCreationDto.getCityId()))
                .setPlacesToVisit(placeService.getByIds(tripCreationDto.getAttractions()));
        Optional.ofNullable(tripCreationDto.getAccommodationId())
                .map(placeService::getById)
                .ifPresent(tripCreationModel::setAccommodation);

        var trip = tripService.create(tripCreationModel);
        tripPlaceService.makeTripRoutes(trip, tripCreationModel);

        var tripCreatedDto = new CreatedDto(trip.getId());

        return new ResponseEntity<>(tripCreatedDto, HttpStatus.CREATED);
    }

}
