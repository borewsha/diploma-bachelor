package com.trip.server.configuration;

import com.trip.server.converter.EnumCaseInsensitiveConverter;
import com.trip.server.database.enumeration.PlaceType;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.*;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE");
    }

    @Override
    public void addFormatters(@NotNull FormatterRegistry registry) {
        var enums = List.of(PlaceType.class);
        enums.forEach(e -> registry.addConverter(String.class, e, new EnumCaseInsensitiveConverter<>(e)));
    }

}
