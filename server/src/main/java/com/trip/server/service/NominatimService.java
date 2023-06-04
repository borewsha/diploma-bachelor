package com.trip.server.service;

import com.trip.server.exception.InternalServerErrorException;
import com.trip.server.model.OsmType;
import com.trip.server.model.OsmIdentifiable;
import fr.dudie.nominatim.client.NominatimClient;
import fr.dudie.nominatim.model.Address;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class NominatimService {

    private final static Integer MAX_SIZE = 50;

    private final NominatimClient nominatimClient;

    public Map<String, Address> lookupByObjects(List<? extends OsmIdentifiable> osmObjects) {
        var osmIds = osmObjects.stream()
                .map(OsmIdentifiable::getOsmId)
                .toList();
        return lookupByIds(osmIds);
    }

    public Map<String, Address> lookupByIds(List<String> osmIds) {
        return ListUtils.partition(osmIds, MAX_SIZE).stream()
                .map(p -> {
                    try {
                        log.debug("IDs to lookup addresses: {}", p);
                        return nominatimClient.lookupAddress(p);
                    } catch (IOException e) {
                        throw new InternalServerErrorException("Ошибка во время обратного геокодирования объектов OSM", e);
                    }
                })
                .flatMap(List::stream)
                .collect(Collectors.toMap(this::getPrefixedId, Function.identity()));
    }

    private String getPrefixedId(Address address) {
        return OsmType.valueOf(address.getOsmType().toUpperCase()).getPrefix() + address.getOsmId();
    }

}
