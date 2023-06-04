package com.trip.server.mapper;

import com.trip.server.database.entity.Place;
import com.trip.server.database.enumeration.PlaceType;
import com.trip.server.dto.PlaceDto;
import com.trip.server.overpass.model.Element;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PlaceMapper implements Mapper {

    public void init(ModelMapper modelMapper) {
        modelMapper.createTypeMap(Element.class, com.trip.server.overpass.entity.Place.class, PlaceType.BUILDING.name())
                .setConverter(mappingContext -> {
                    var element = mappingContext.getSource();
                    return new com.trip.server.overpass.entity.Place(
                            element.getType().getPrefixedId(element.getId()),
                            PlaceType.BUILDING,
                            element.getTags().getOrDefault("name", null),
                            element.getTags().get("addr:street") + ", " + element.getTags().get("addr:housenumber"),
                            element.getLat(),
                            element.getLon()
                    );
                });

        modelMapper.createTypeMap(Element.class, com.trip.server.overpass.entity.Place.class, PlaceType.TOURISM.name())
                .setConverter(mappingContext -> {
                    var element = mappingContext.getSource();
                    return new com.trip.server.overpass.entity.Place(
                            element.getType().getPrefixedId(element.getId()),
                            PlaceType.TOURISM,
                            element.getTags().getOrDefault("name", element.getTags().get("description")),
                            null,
                            element.getLat(),
                            element.getLon()
                    );
                });

        modelMapper.createTypeMap(Place.class, PlaceDto.class)
                .setConverter(mappingContext -> {
                    var place = mappingContext.getSource();
                    return new PlaceDto(
                            place.getId(),
                            place.getImage() != null ? place.getImage().getId() : null,
                            place.getOsmId(),
                            place.getName(),
                            place.getAddress(),
                            place.getLat(),
                            place.getLon()
                    );
                });
    }

}
