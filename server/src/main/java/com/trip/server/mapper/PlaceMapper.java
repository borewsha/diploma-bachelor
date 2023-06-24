package com.trip.server.mapper;

import com.trip.server.database.entity.Place;
import com.trip.server.database.enumeration.PlaceType;
import com.trip.server.dto.place.PlaceDto;
import com.trip.server.overpass.model.Element;
import org.modelmapper.ModelMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class PlaceMapper implements Mapper {

    public void init(ModelMapper modelMapper) {
        modelMapper.createTypeMap(Element.class, com.trip.server.overpass.entity.Place.class)
                .setConverter(mappingContext -> {
                    var element = mappingContext.getSource();

                    return new com.trip.server.overpass.entity.Place(
                            getOsmId(element),
                            getPlaceType(element),
                            getName(element),
                            getAddress(element),
                            element.getLat(),
                            element.getLon()
                    );
                });

        modelMapper.createTypeMap(Place.class, PlaceDto.class)
                .setConverter(mappingContext -> {
                    var place = mappingContext.getSource();
                    return new PlaceDto(
                            place.getId(),
                            place.getCity().getId(),
                            getImageId(place),
                            place.getType().name().toLowerCase(),
                            place.getName(),
                            place.getAddress(),
                            place.getLat(),
                            place.getLon()
                    );
                });
    }

    public static String getOsmId(Element element) {
        return element.getType().getPrefixedId(element.getId());
    }

    public static PlaceType getPlaceType(Element element) {
        if (element.getTags().containsKey("building")) {
            return PlaceType.BUILDING;
        }
        if (element.getTags().containsKey("tourism")) {
            return PlaceType.TOURISM;
        }
        return PlaceType.CUSTOM;
    }

    @Nullable
    public static String getName(Element element) {
        return element.getTags().getOrDefault("name", element.getTags().getOrDefault("description", null));
    }

    @Nullable
    public static String getAddress(Element element) {
        return element.getTags().containsKey("addr:street") && element.getTags().containsKey("addr:housenumber")
                ? element.getTags().get("addr:street") + ", " + element.getTags().get("addr:housenumber")
                : null;
    }

    @Nullable
    public static Long getImageId(Place place) {
        return place.getImage() != null ? place.getImage().getId() : null;
    }

}
