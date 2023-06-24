package com.trip.server.model;

import com.trip.server.database.entity.Image;
import lombok.*;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CityPatchModel implements Patchable {

    @Nullable
    private Image image;

    private boolean imageSet;

    @Override
    public Boolean isEmpty() {
        return !isImageSet();
    }

}
