package com.trip.server.osrm;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.trip.server.exception.InternalServerErrorException;
import com.trip.server.osrm.request.Waypoint;
import com.trip.server.osrm.response.RouteResponse;
import com.trip.server.osrm.response.TableResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class OsrmClient {

    private final String scheme;

    private final String host;

    private final Integer port;

    private final ObjectMapper objectMapper;

    private final JsonMapper jsonMapper;

    private final HttpClient httpClient = HttpClients.createDefault();

    private final TypeReference<RouteResponse> routeTypeReference = new TypeReference<>() {
    };

    private final TypeReference<TableResponse> tableTypeReference = new TypeReference<>() {
    };

    public OsrmClient(String scheme, String host, Integer port, ObjectMapper objectMapper, JsonMapper jsonMapper) {
        this.scheme = scheme;
        this.host = host;
        this.port = port;
        this.objectMapper = objectMapper;
        this.jsonMapper = jsonMapper;
    }

    public RouteResponse route(List<Waypoint> waypoints) {
        var overview = "false";
        var steps = true;
        var request = getRouteRequest(waypoints, overview, steps);

        return getResponse(request, routeTypeReference);
    }

    public TableResponse table(List<Waypoint> waypoints) {
        var annotations = "duration,distance";
        var request = getTableRequest(waypoints, annotations);

        return getResponse(request, tableTypeReference);
    }

    private UriBuilder getUriBuilder() {
        return new DefaultUriBuilderFactory().builder()
                .scheme(scheme)
                .host(host)
                .port(port);
    }

    private HttpUriRequest getRouteRequest(List<Waypoint> waypoints, String overview, Boolean steps) {
        var uri = getUriBuilder()
                .pathSegment("route", "v1", "driving", waypoints.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(";"))
                )
                .queryParam("overview", overview)
                .queryParam("steps", steps)
                .build();

        return new HttpGet(uri);
    }

    private HttpUriRequest getTableRequest(List<Waypoint> waypoints, String annotations) {
        var uri = getUriBuilder()
                .pathSegment("table", "v1", "driving", waypoints.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(";"))
                )
                .queryParam("annotations", annotations)
                .build();

        return new HttpGet(uri);
    }

    private <T> T getResponse(HttpUriRequest request, TypeReference<T> typeReference) {
        request.addHeader("accept", "application/json");

        try {
            var response = httpClient.execute(request);
            var json = jsonMapper.readTree(response.getEntity().getContent());

            return objectMapper.convertValue(json, typeReference);
        } catch (IOException ignored) {
            throw new InternalServerErrorException("Произошла ошибка во время обращения к OSRM");
        }
    }

}
