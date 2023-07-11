package com.trip.server.dto.trip;

import com.trip.server.dto.city.CityDto;
import com.trip.server.dto.place.PlaceDto;
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
public class TripExtendedDto implements Identifiable {

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
            description = "Объект города"
    )
    private CityDto city;

    @Schema(
            description = "Объект места для ночлега"
    )
    private PlaceDto accommodation;

    @Schema(
            description = "Дата начала путешествия"
    )
    private List<String> dates;

    @Schema(
            description = "Дни путешествия с местами для посещения"
    )
    private List<DayDto> days;

}
