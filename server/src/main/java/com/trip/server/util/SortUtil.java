package com.trip.server.util;

import com.trip.server.dto.SortParamsDto;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;

import java.util.Optional;

@UtilityClass
public final class SortUtil {

    public static final Sort.Direction DEFAULT_DIRECTION = Sort.Direction.ASC;
    public static final String DEFAULT_SORT_BY = "id";

    public static Sort.Direction getDirection(@Nullable Boolean ascending) {
        return Optional.ofNullable(ascending)
                .map(a -> a ? Sort.Direction.ASC : Sort.Direction.DESC)
                .orElse(DEFAULT_DIRECTION);
    }

    public static String getSortBy(@Nullable String sortBy) {
        return sortBy == null ? DEFAULT_SORT_BY : sortBy;
    }

    public static Sort request(@Nullable Boolean ascending, @Nullable String sortBy) {
        return Sort.by(getDirection(ascending), getSortBy(sortBy));
    }

    public static Sort request(SortParamsDto sortParamsDto) {
        return request(sortParamsDto.getAscending(), sortParamsDto.getSortBy());
    }

}
