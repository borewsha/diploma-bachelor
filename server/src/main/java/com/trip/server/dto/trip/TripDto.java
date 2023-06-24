package com.trip.server.dto.trip;

import com.trip.server.model.Identifiable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class TripDto implements Identifiable {

    @Schema(
            description = "ID в базе данных приложения",
            example = "6"
    )
    private Long id;

    @Schema(
            description = "ID пользователя в базе данных",
            example = "5"
    )
    private Long userId;

    @Schema(
            description = "ID города в базе данных",
            example = "1"
    )
    private Long cityId;

    @Schema(
            description = "ID места для ночлега",
            example = "1",
            nullable = true
    )
    @Nullable
    private Long accommodationId;

    @Schema(
            description = "ID изображения города в базе данных",
            example = "2",
            nullable = true
    )
    @Nullable
    private Long cityImageId;

    @Schema(
            description = "Название города путешествия",
            example = "Хабаровск"
    )
    private String cityName;

    @Schema(
            description = "Дата начала путешествия"
    )
    private List<String> dates;

}
