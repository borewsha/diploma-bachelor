package com.trip.server.controller;

import com.trip.server.database.enumeration.PlaceType;
import com.trip.server.dto.city.CityDto;
import com.trip.server.dto.city.CityPatchDto;
import com.trip.server.dto.error.ApiErrorDto;
import com.trip.server.dto.error.InvalidFieldsDto;
import com.trip.server.dto.PageDto;
import com.trip.server.dto.PageParamsDto;
import com.trip.server.dto.place.PlaceDto;
import com.trip.server.model.CityPatchModel;
import com.trip.server.service.CityService;
import com.trip.server.service.ImageService;
import com.trip.server.service.PlaceService;
import com.trip.server.util.PageUtil;
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
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Validated
@AllArgsConstructor
@RestController
@Tag(name = "Cities")
@RequestMapping("/api/cities")
public class CityController extends ApiController {

    private final ModelMapper modelMapper;

    private final CityService cityService;

    private final ImageService imageService;

    private final PlaceService placeService;

    @Operation(summary = "Список городов, отсортированный по населению")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список городов успешно отдан"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некоторые поля не прошли валидацию",
                    content = @Content(schema = @Schema(implementation = InvalidFieldsDto.class))
            )
    })
    @GetMapping
    public ResponseEntity<PageDto<CityDto>> getAll(
            @Valid PageParamsDto pageParamsDto,
            @RequestParam(required = false) String search
    ) {
        var pageRequest = PageUtil.request(pageParamsDto);
        var page = cityService.getAll(search, pageRequest);
        var pageDto = PageUtil.toDto(modelMapper, page, CityDto.class);

        return new ResponseEntity<>(pageDto, HttpStatus.OK);
    }

    @Operation(summary = "Поиск города")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Данные о городе успешно отданы"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Город не найден",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<CityDto> getById(@PathVariable Long id) {
        var city = cityService.getById(id);
        var cityDto = modelMapper.map(city, CityDto.class);

        return new ResponseEntity<>(cityDto, HttpStatus.OK);
    }

    @Operation(summary = "Список всех мест в городе")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список всех мест в городе успешно отдан"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некоторые поля не прошли валидацию",
                    content = @Content(schema = @Schema(implementation = InvalidFieldsDto.class))
            )
    })
    @GetMapping("/{id}/places")
    public ResponseEntity<PageDto<PlaceDto>> getPlaces(
            @PathVariable Long id,
            @Valid PageParamsDto pageParamsDto,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Set<PlaceType> type
    ) {
        var pageRequest = PageUtil.request(pageParamsDto);
        var city = cityService.getById(id);
        var page = placeService.getByCity(city, search, type, pageRequest);
        var pageDto = PageUtil.toDto(modelMapper, page, PlaceDto.class);

        return new ResponseEntity<>(pageDto, HttpStatus.OK);
    }

    @Operation(summary = "Обновить данные о городе")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Данные о городе успешно обновлены"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некоторые поля не прошли валидацию",
                    content = @Content(schema = @Schema(implementation = InvalidFieldsDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = """
                            Возможные ошибки:
                            - Город не найден
                            - Изображение не найдено
                            """,
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))
            )
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    schema = @Schema(
                            implementation = CityPatchDto.class
                    )
            )
    )
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<?> patch(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        var cityPatchDto = modelMapper.map(body, CityPatchDto.class);
        var cityPatchModel = modelMapper.map(cityPatchDto, CityPatchModel.class);

        cityPatchModel.setImageSet(body.containsKey("imageId"));
        if (cityPatchModel.isImageSet()) {
            Optional.ofNullable(cityPatchDto.getImageId())
                    .map(imageService::getById)
                    .ifPresent(cityPatchModel::setImage);
        }

        cityService.patch(id, cityPatchModel);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
