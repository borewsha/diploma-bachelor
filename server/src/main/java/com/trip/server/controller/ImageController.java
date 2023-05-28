package com.trip.server.controller;

import java.io.*;

import com.trip.server.dto.ApiErrorDto;
import com.trip.server.dto.CreatedDto;
import com.trip.server.exception.BadRequestException;
import com.trip.server.service.ImageService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import org.springframework.core.io.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

@AllArgsConstructor
@RestController
@Tag(name = "Images")
@RequestMapping("/api/images")
public class ImageController extends ApiController {

    private final ImageService imageService;

    @Operation(summary = "Поиск изображения")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Изображение найдено"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Изображение не найдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))
            )
    })
    @GetMapping(value = "/{id}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public Resource getById(@PathVariable Long id) {
        var image = imageService.getById(id);
        return new ByteArrayResource(image.getContent());
    }

    @Operation(summary = "Загрузка фотографии")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Переданный файл не может быть загружен"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла ошибка на сервере",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class))
            )
    })
    @PostMapping
    public ResponseEntity<CreatedDto> save(@RequestParam MultipartFile image) {
        try {
            byte[] imageBytes = image.getBytes();

            var imageId = imageService.save(imageBytes);
            var imageCreatedDto = new CreatedDto(imageId);

            return ResponseEntity.ok(imageCreatedDto);
        } catch (IOException e) {
            throw new BadRequestException("Произошла ошибка при загрузке фотографии", e);
        }
    }

}
