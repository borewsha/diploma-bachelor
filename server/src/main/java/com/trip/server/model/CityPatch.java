package com.trip.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CityPatch implements Patch {

    @Nullable
    private Long imageId;

    @JsonIgnore
    private boolean imageIdSet;

    @Override
    public Boolean isEmpty() {
        return !isImageIdSet();
    }
}
