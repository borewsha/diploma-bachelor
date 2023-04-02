package com.trip.server.util;

import java.time.format.*;
import java.time.temporal.*;

import lombok.experimental.*;

@UtilityClass
public final class TimeUtil {

    public static String getFormattedZonedDateTime(TemporalAccessor temporalAccessor) {
        return DateTimeFormatter.ISO_INSTANT.format(temporalAccessor);
    }

}
