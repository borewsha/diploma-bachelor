package com.trip.server.controller;

import com.trip.server.dto.*;
import com.trip.server.model.CityPatch;
import com.trip.server.provider.IdentificationProvider;
import com.trip.server.service.CityService;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Validated
@AllArgsConstructor
@RestController
@Tag(name = "Cities")
@RequestMapping("/api/cities")
public class CityController extends ApiController {

    private final ModelMapper modelMapper;

    private final CityService cityService;

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
            )
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    schema = @Schema(
                            implementation = CityPatch.class
                    )
            )
    )
    @PatchMapping("/{id}")
    public ResponseEntity<?> patch(
            @PathVariable String id,
            @RequestParam(required = false) IdentificationProvider provider,
            @RequestBody Map<String, Object> body
    ) {
        var cityPatch = modelMapper.map(body, CityPatch.class);
        cityPatch.setImageIdSet(body.containsKey("imageId"));
        cityService.patch(id, provider, cityPatch);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
