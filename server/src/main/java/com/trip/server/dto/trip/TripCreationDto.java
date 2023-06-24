package com.trip.server.dto.trip;

import com.trip.server.validator.TripAttractions;
import com.trip.server.validator.TripDates;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Schema(description = "Создание путешествия")
public class TripCreationDto {

    @Schema(
            description = "ID пользователя, для которого будет создаваться путешествие",
            example = "3"
    )
    @NotNull
    private Long userId;

    @Schema(
            description = "ID города",
            example = "2"
    )
    @NotNull
    private Long cityId;

    @Schema(
            description = "ID места для ночлега",
            example = "54",
            nullable = true
    )
    @Nullable
    private Long accommodationId;

    @Schema(
            description = "Даты путешествия"
    )
    @NotNull
    @TripDates
    private List<LocalDate> dates;

    @Schema(
            description = "Список ID мест для посещения"
    )
    @NotNull
    @TripAttractions
    private List<Long> attractions;

}
