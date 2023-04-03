package com.trip.server.mapper;

import com.trip.server.overpass.entity.City;
import com.trip.server.overpass.model.Element;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CityMapper implements Mapper {

    public void init(ModelMapper modelMapper) {
        modelMapper.createTypeMap(Element.class, City.class).setConverter(mappingContext -> {
            var element = mappingContext.getSource();
            return new City(
                    element.getId(),
                    element.getTags().get("name"),
                    element.getTags().getOrDefault("addr:region", null),
                    element.getLat(),
                    element.getLon()
            );
        });
    }

}
