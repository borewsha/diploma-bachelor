package com.trip.server.util;

import com.trip.server.dto.DataDto;
import com.trip.server.dto.GeoFilterParamsDto;
import com.trip.server.overpass.model.GeoFilters;
import lombok.experimental.UtilityClass;
import org.modelmapper.ModelMapper;

import java.util.List;

@UtilityClass
public final class DataUtil {

    public static <T, C> DataDto<C> toDto(ModelMapper mapper, List<T> data, Class<C> contentType) {
        return DataDto.<C>builder()
                .totalElements(data.size())
                .data(data.stream()
                        .map(e -> mapper.map(e, contentType))
                        .toList()
                )
                .build();
    }

    public static GeoFilters filters(GeoFilterParamsDto geoFilterParamsDto) {
        return new GeoFilters(geoFilterParamsDto.getBbox(), geoFilterParamsDto.getRadius());
    }

}
