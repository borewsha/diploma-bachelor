package com.trip.server.overpass.reader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.trip.server.overpass.model.Element;
import de.westnordost.osmapi.ApiResponseReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JsonResponseReader implements ApiResponseReader<List<Element>> {

    private final JsonMapper jsonMapper;

    private final ObjectMapper objectMapper;

    private final TypeReference<List<Element>> elementListTypeReference = new TypeReference<>() {
    };

    protected List<Element> getElements(InputStream inputStream) throws IOException {
        var arrayNode = (ArrayNode) jsonMapper.readTree(inputStream).get("elements");
        return objectMapper.convertValue(arrayNode, elementListTypeReference);
    }

    @Override
    public List<Element> parse(InputStream inputStream) throws Exception {
        return getElements(inputStream);
    }

    public static StringBuilder getQueryBuilder() {
        return new StringBuilder().append("[out:json];");
    }

}
