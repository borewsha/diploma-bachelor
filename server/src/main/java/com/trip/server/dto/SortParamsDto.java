package com.trip.server.dto;

import com.trip.server.util.SortUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сортировка")
public class SortParamsDto {

    @Schema(
            description = "Направление сортировки",
            example = "true",
            nullable = true
    )
    private Boolean ascending = SortUtil.DEFAULT_DIRECTION.equals(Sort.Direction.ASC);

    @Schema(
            description = "Поле, по которому происходит сортировка",
            example = SortUtil.DEFAULT_SORT_BY,
            nullable = true
    )
    private String sortBy = SortUtil.DEFAULT_SORT_BY;

}
