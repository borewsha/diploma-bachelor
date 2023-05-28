package com.trip.server.service;

import com.trip.server.exception.InternalServerErrorException;
import com.trip.server.model.OsmType;
import com.trip.server.model.OsmIdentifiable;
import fr.dudie.nominatim.client.NominatimClient;
import fr.dudie.nominatim.model.Address;
import lombok.AllArgsConstructor;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NominatimService {

    private final NominatimClient nominatimClient;

    public Map<Long, Address> lookupNodes(List<? extends OsmIdentifiable> osmIds) {
        return lookup(osmIds, OsmType.NODE);
    }

    public Map<Long, Address> lookupWays(List<? extends OsmIdentifiable> osmIds) {
        return lookup(osmIds, OsmType.WAY);
    }

    public Map<Long, Address> lookupRelations(List<? extends OsmIdentifiable> osmIds) {
        return lookup(osmIds, OsmType.RELATION);
    }

    private Map<Long, Address> lookup(List<? extends OsmIdentifiable> osmIds, OsmType osmType) {
        var prefixedOsmIds = toOsmIds(osmIds, osmType);
        try {
            // TODO: убрать ограничение в максимум 50 объектов в одном запросе
            return nominatimClient.lookupAddress(prefixedOsmIds).stream()
                    .collect(Collectors.toMap(a -> Long.parseLong(a.getOsmId()), Function.identity()));
        } catch (IOException e) {
            throw new InternalServerErrorException("Ошибка во время обратного геокодирования объектов OSM", e);
        }
    }

    private List<String> toOsmIds(List<? extends OsmIdentifiable> identifiableList, OsmType osmType) {
        return identifiableList.stream()
                .map(identifiable -> osmType.getPrefix() + identifiable.getOsmId())
                .toList();
    }

}
