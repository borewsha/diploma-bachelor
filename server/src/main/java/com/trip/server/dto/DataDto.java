package com.trip.server.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные")
public class DataDto<T> {

    @Schema(
            description = "Количество элементов",
            example = "55"
    )
    private Integer totalElements;

    @Schema(
            description = "Возвращаемые сервером данные"
    )
    private List<T> data;

}
