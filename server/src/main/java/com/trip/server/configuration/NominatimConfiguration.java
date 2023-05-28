package com.trip.server.configuration;

import fr.dudie.nominatim.client.JsonNominatimClient;
import fr.dudie.nominatim.client.NominatimClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class NominatimConfiguration {

    @Value("${nominatim.host}")
    private String host;

    @Value("${nominatim.port}")
    private Integer port;

    @Bean
    public NominatimClient nominatimClient() {
        var apiUrl = new DefaultUriBuilderFactory().builder()
                .scheme("http")
                .host(host)
                .port(port)
                .build();
        var httpClient = HttpClients.createDefault();
        return new JsonNominatimClient(apiUrl.toString(), httpClient, "");
    }

}
