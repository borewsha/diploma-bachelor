package com.trip.server.mapper;

import com.trip.server.overpass.entity.Place;
import com.trip.server.overpass.model.Element;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PlaceMapper implements Mapper {

    public void init(ModelMapper modelMapper) {
        modelMapper.createTypeMap(Element.class, Place.class).setConverter(mappingContext -> {
            var element = mappingContext.getSource();

            return new Place(
                    element.getId(),
                    null,
                    element.getTags().getOrDefault("name", null),
                    String.format("%s, %s", element.getTags().get("addr:street"), element.getTags().get("addr:housenumber")),
                    element.getLat(),
                    element.getLon()
            );
        });
    }

}
