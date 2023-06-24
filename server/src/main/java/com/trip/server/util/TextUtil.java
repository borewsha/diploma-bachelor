package com.trip.server.util;

import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

@UtilityClass
public final class TextUtil {

    private static final Pattern CIRILLIC_PATTERN = Pattern.compile("\\p{InCyrillic}+");

    private static final Pattern EXTRA_CHARS_PATTERN = Pattern.compile("[\\s.,:()\\-_*!?]+");

    public static boolean containsCyrillic(String text) {
        return CIRILLIC_PATTERN.matcher(text).find();
    }

    public static String minimize(String string) {
        return EXTRA_CHARS_PATTERN.matcher(string).replaceAll("");
    }

}
