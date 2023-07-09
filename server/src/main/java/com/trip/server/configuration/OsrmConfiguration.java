package com.trip.server.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.trip.server.osrm.OsrmClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OsrmConfiguration {

    @Value("${osrm.car.host}")
    private String carHost;

    @Value("${osrm.car.port}")
    private Integer carPort;

    @Value("${osrm.foot.host}")
    private String footHost;

    @Value("${osrm.foot.port}")
    private Integer footPort;

    @Bean
    public OsrmClient osrmClientCar(ObjectMapper objectMapper, JsonMapper jsonMapper) {
        return new OsrmClient("http", carHost, carPort, objectMapper, jsonMapper);
    }

    @Bean
    public OsrmClient osrmClientFoot(ObjectMapper objectMapper, JsonMapper jsonMapper) {
        return new OsrmClient("http", footHost, footPort, objectMapper, jsonMapper);
    }

}
