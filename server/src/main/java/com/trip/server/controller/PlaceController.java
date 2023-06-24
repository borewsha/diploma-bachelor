package com.trip.server.controller;

import com.trip.server.dto.error.ApiErrorDto;
import com.trip.server.dto.error.InvalidFieldsDto;
import com.trip.server.dto.DataDto;
import com.trip.server.dto.GeoFiltersParamsDto;
import com.trip.server.dto.PageDto;
import com.trip.server.dto.PageParamsDto;
import com.trip.server.dto.place.PlaceDto;
import com.trip.server.dto.place.PlacePatchDto;
import com.trip.server.model.PlacePatchModel;
import com.trip.server.service.CityService;
import com.trip.server.service.ImageService;
import com.trip.server.service.PlaceService;
import com.trip.server.util.DataUtil;
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
import javax.validation.constraints.NotNull;
import java.util.Map;

@RestController
@AllArgsConstructor
@Tag(name = "Places")
@RequestMapping("/api/places")
@Validated
public class PlaceController extends ApiController {

    private final ModelMapper modelMapper;

    private final PlaceService placeService;

    private final CityService cityService;

    private final ImageService imageService;

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
    @GetMapping
    public ResponseEntity<PageDto<PlaceDto>> getAll(
            @Valid PageParamsDto pageParamsDto,
            @RequestParam @NotNull Long cityId,
            @RequestParam(required = false) String search
    ) {
        var pageRequest = PageUtil.request(pageParamsDto);
        var city = cityService.getById(cityId);
        var page = placeService.getByCity(city, search, pageRequest);
        var pageDto = PageUtil.toDto(modelMapper, page, PlaceDto.class);

        return new ResponseEntity<>(pageDto, HttpStatus.OK);
    }

    @Operation(summary = "Список зданий в городе")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список зданий в городе успешно отдан"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некоторые поля не прошли валидацию",
                    content = @Content(schema = @Schema(implementation = InvalidFieldsDto.class))
            )
    })
    @GetMapping("/buildings")
    public ResponseEntity<PageDto<PlaceDto>> getBuildings(
            @Valid PageParamsDto pageParamsDto,
            @RequestParam @NotNull Long cityId,
            @RequestParam(required = false) String search
    ) {
        var pageRequest = PageUtil.request(pageParamsDto);
        var city = cityService.getById(cityId);
        var page = placeService.getBuildingsByCity(city, search, pageRequest);
        var pageDto = PageUtil.toDto(modelMapper, page, PlaceDto.class);

        return new ResponseEntity<>(pageDto, HttpStatus.OK);
    }

    @Operation(summary = "Список туристических мест в городе")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список туристических мест в городе успешно отдан"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некоторые поля не прошли валидацию",
                    content = @Content(schema = @Schema(implementation = InvalidFieldsDto.class))
            )
    })
    @GetMapping("/tourism")
    public ResponseEntity<DataDto<PlaceDto>> getTourism(
            @Valid GeoFiltersParamsDto geoFilterParamsDto,
            @RequestParam @NotNull Long cityId,
            @RequestParam(required = false) String search
    ) {
        var filters = DataUtil.filters(geoFilterParamsDto);
        var city = cityService.getById(cityId);
        var data = placeService.getTourismByCity(city, search, filters);
        var pageDto = DataUtil.toDto(modelMapper, data, PlaceDto.class);

        return new ResponseEntity<>(pageDto, HttpStatus.OK);
    }

    @Operation(summary = "Поиск места")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Место успешно отдано"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Место не найдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<PlaceDto> getById(@PathVariable Long id) {
        var place = placeService.getById(id);
        var placeDto = modelMapper.map(place, PlaceDto.class);

        return new ResponseEntity<>(placeDto, HttpStatus.OK);
    }

    @Operation(summary = "Обновить данные о месте")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Данные о месте успешно обновлены"
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
                            - Место не найдено
                            - Изображение не найдено
                            """,
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))
            )
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    schema = @Schema(implementation = PlacePatchDto.class)
            )
    )
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<?> patch(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        var placePatchDto = modelMapper.map(body, PlacePatchDto.class);
        var placePatchModel = modelMapper.map(placePatchDto, PlacePatchModel.class);
        if (body.containsKey("imageId")) {
            placePatchModel.setImage(imageService.getById(placePatchDto.getImageId()));
        }

        placeService.patch(id, placePatchModel);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
