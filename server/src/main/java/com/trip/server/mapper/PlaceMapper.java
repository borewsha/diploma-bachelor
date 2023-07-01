package com.trip.server.mapper;

import com.trip.server.database.entity.Place;
import com.trip.server.database.enumeration.PlaceType;
import com.trip.server.dto.place.PlaceDto;
import com.trip.server.overpass.model.Element;
import org.modelmapper.ModelMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

@Component
public class PlaceMapper implements Mapper {

    private final static List<String> HOTElS = List.of("hotel", "hostel", "apartment", "guest_house");

    private final static List<String> MONUMENTS = List.of("monument", "memorial");

    public final static Map<Predicate<Element>, PlaceType> TYPES = Map.of(
            PlaceMapper::isAttraction, PlaceType.ATTRACTION,
            PlaceMapper::isCinema, PlaceType.CINEMA,
            PlaceMapper::isHotel, PlaceType.HOTEL,
            PlaceMapper::isHouse, PlaceType.HOUSE,
            PlaceMapper::isMonument, PlaceType.MONUMENT,
            PlaceMapper::isMuseum, PlaceType.MUSEUM,
            PlaceMapper::isTheatre, PlaceType.THEATRE,
            PlaceMapper::isViewpoint, PlaceType.VIEWPOINT
    );

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
        return TYPES.entrySet().stream()
                .filter(e -> e.getKey().test(element))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(PlaceType.UNKNOWN);
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

    public static boolean isAttraction(Element e) {
        return Objects.equals(e.getTags().get("tourism"), "attraction");
    }

    public static boolean isCinema(Element e) {
        return Objects.equals(e.getTags().get("amenity"), "cinema");
    }

    public static boolean isHotel(Element e) {
        return Optional.ofNullable(e.getTags().get("tourism"))
                .map(HOTElS::contains)
                .orElse(false);
    }

    public static boolean isHouse(Element e) {
        return e.getTags().containsKey("building") && !e.getTags().containsKey("name");
    }

    public static boolean isMonument(Element e) {
        if (isAttraction(e)) {
            return false;
        }

        return Optional.ofNullable(e.getTags().get("historic"))
                .map(MONUMENTS::contains)
                .orElse(false);
    }

    public static boolean isMuseum(Element e) {
        return Objects.equals(e.getTags().get("tourism"), "museum");
    }

    public static boolean isTheatre(Element e) {
        return Objects.equals(e.getTags().get("amenity"), "theatre");
    }

    public static boolean isViewpoint(Element e) {
        return Objects.equals(e.getTags().get("tourism"), "viewpoint");
    }

}
