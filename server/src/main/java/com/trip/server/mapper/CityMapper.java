package com.trip.server.mapper;

import com.trip.server.database.entity.City;
import com.trip.server.dto.CityDto;
import com.trip.server.overpass.model.Element;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CityMapper implements Mapper {

    public void init(ModelMapper modelMapper) {
        modelMapper.createTypeMap(Element.class, com.trip.server.overpass.entity.City.class).setConverter(mappingContext -> {
            var element = mappingContext.getSource();
            return new com.trip.server.overpass.entity.City(
                    element.getId(),
                    element.getTags().get("name"),
                    element.getTags().getOrDefault("addr:region", null),
                    element.getLat(),
                    element.getLon()
            );
        });

        modelMapper.createTypeMap(City.class, CityDto.class).setConverter(mappingContext -> {
            var city = mappingContext.getSource();
            return new CityDto(
                    city.getId(),
                    city.getImage() != null ? city.getImage().getId() : null,
                    city.getOsmId(),
                    city.getName(),
                    city.getRegion(),
                    city.getLat(),
                    city.getLon()
            );
        });

    }

}
