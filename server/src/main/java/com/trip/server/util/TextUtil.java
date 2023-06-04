package com.trip.server.util;

import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

@UtilityClass
public final class TextUtil {

    private static final Pattern cirillicPattern = Pattern.compile("\\p{InCyrillic}+");

    public static boolean containsCyrillic(String text) {
        return cirillicPattern.matcher(text).find();
    }

}
