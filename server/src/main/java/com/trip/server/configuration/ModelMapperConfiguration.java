package com.trip.server.configuration;

import java.util.*;

import com.trip.server.mapper.*;
import org.modelmapper.*;
import org.modelmapper.convention.*;
import org.springframework.context.annotation.*;

import static org.modelmapper.config.Configuration.AccessLevel;

@Configuration
public class ModelMapperConfiguration {

    @Bean
    public ModelMapper modelMapper(List<Mapper> mappers) {
        var modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.STRICT)
            .setFieldAccessLevel(AccessLevel.PRIVATE)
            .setFieldMatchingEnabled(true)
            .setSkipNullEnabled(true);

        mappers.forEach(m -> m.init(modelMapper));

        return modelMapper;
    }

}