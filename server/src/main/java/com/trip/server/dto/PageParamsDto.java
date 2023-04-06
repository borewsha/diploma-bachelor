package com.trip.server.dto;

import javax.validation.constraints.*;

import com.trip.server.util.PageUtil;
import io.swagger.v3.oas.annotations.media.*;
import lombok.*;
import org.springframework.lang.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Параметры пагинации")
public class PageParamsDto {

    @Schema(
            description = "Номер страницы",
            example = "" + PageUtil.MIN_PAGE
    )
    @Nullable
    @Min(value = PageUtil.MIN_PAGE, message = PageUtil.PAGE_ERROR)
    private Integer page = PageUtil.MIN_PAGE;

    @Schema(
            description = "Размер страницы (количество элементов)",
            example = "" + PageUtil.MAX_SIZE
    )
    @Nullable
    @Min(value = PageUtil.MIN_SIZE, message = PageUtil.SIZE_ERROR)
    private Integer size = PageUtil.MAX_SIZE;

}
