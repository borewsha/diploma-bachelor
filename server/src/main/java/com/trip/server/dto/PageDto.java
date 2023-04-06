package com.trip.server.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Пагинация")
public class PageDto<T> {

    @Schema(
            description = "Номер страницы",
            example = "3"
    )
    private Integer page;

    @Schema(
            description = "Количество элементов",
            example = "40"
    )
    private Integer size;

    @Schema(
            description = "Возможное количество страниц",
            example = "15"
    )
    private Integer totalPages;

    @Schema(
            description = "Возможное количество элементов",
            example = "203"
    )
    private Long totalElements;

    @Schema(
            description = "Данные страницы"
    )
    private List<T> data;

}
