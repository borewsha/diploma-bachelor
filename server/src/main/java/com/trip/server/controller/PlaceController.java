package com.trip.server.controller;

import com.trip.server.dto.error.ApiErrorDto;
import com.trip.server.dto.error.InvalidFieldsDto;
import com.trip.server.dto.place.PlaceDto;
import com.trip.server.dto.place.PlacePatchDto;
import com.trip.server.model.PlacePatchModel;
import com.trip.server.service.ImageService;
import com.trip.server.service.PlaceService;
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

import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Tag(name = "Places")
@RequestMapping("/api/places")
@Validated
public class PlaceController extends ApiController {

    private final ModelMapper modelMapper;

    private final PlaceService placeService;

    private final ImageService imageService;

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

        placePatchModel.setImageSet(body.containsKey("imageId"));
        if (placePatchModel.isImageSet()) {
            Optional.ofNullable(placePatchDto.getImageId())
                    .map(imageService::getById)
                    .ifPresent(placePatchModel::setImage);
        }

        placeService.patch(id, placePatchModel);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
