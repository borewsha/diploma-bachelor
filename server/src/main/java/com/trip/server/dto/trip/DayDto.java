package com.trip.server.dto.trip;

import com.trip.server.dto.place.PlaceDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class DayDto {

    private String date;

    private List<PlaceDto> places;

    private List<WayDto> ways;

}
