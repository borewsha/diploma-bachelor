package com.trip.server.controller;

import com.trip.server.dto.*;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@Tag(name = "Places")
@RequestMapping("/api/places")
@Validated
public class PlaceController extends ApiController {

    private final ModelMapper modelMapper;

    private final PlaceService placeService;

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
            @RequestParam String city,
            @RequestParam(required = false) String search
    ) {
        var pageRequest = PageUtil.request(pageParamsDto);
        var page = placeService.getBuildings(city, search, pageRequest);
        var pageDto = PageUtil.toDto(modelMapper, page, PlaceDto.class);

        return new ResponseEntity<>(pageDto, HttpStatus.OK);
    }

}
