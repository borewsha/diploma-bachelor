package com.trip.server.overpass.repository;

import com.trip.server.overpass.model.Element;
import com.trip.server.util.TextUtil;
import de.westnordost.osmapi.ApiResponseReader;
import de.westnordost.osmapi.overpass.OverpassMapDataApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
public abstract class Repository {

    private final OverpassMapDataApi overpassMapDataApi;

    private final ApiResponseReader<List<Element>> apiResponseReader;

    protected List<Element> getResponse(String query, @Nullable String search) {
        log.debug("Overpass query: {}", query);

        var response = overpassMapDataApi.query(query, apiResponseReader);

        return Optional.ofNullable(search)
                .filter(s -> !s.isBlank())
                .map(this::filterBySearchRequest)
                .map(p -> response.stream().filter(p).toList())
                .orElse(response);
    }

    protected Predicate<Element> filterBySearchRequest(String search) {
        var searchRequest = TextUtil.minimize(search);

        return e -> getFilteredFields(e)
                .filter(Objects::nonNull)
                .map(TextUtil::minimize)
                .anyMatch(s -> StringUtils.containsIgnoreCase(s, searchRequest));
    }

    protected abstract Stream<String> getFilteredFields(Element e);

}
