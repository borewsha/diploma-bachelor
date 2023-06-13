package com.trip.server.configuration;

import de.westnordost.osmapi.OsmConnection;
import de.westnordost.osmapi.overpass.OverpassMapDataApi;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class OverpassConfiguration {

    @Value("${overpass.host}")
    private String host;

    @Value("${overpass.port}")
    private Integer port;

    @Bean
    public OverpassMapDataApi overpassMapDataApi() {
        var apiUrl = new DefaultUriBuilderFactory().builder()
                .scheme("http")
                .host(host)
                .port(port)
                .path("api/")
                .build();
        var connection = new OsmConnection(apiUrl.toString(), "User-Agent");
        return new OverpassMapDataApi(connection);
    }

}
