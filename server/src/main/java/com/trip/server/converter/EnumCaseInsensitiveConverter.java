package com.trip.server.converter;

import org.springframework.core.convert.converter.Converter;

public class EnumCaseInsensitiveConverter<T extends Enum<T>> implements Converter<String, T> {

    private final Class<T> enumClass;

    public EnumCaseInsensitiveConverter(Class<T> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public T convert(String from) {
        return T.valueOf(enumClass, from.toUpperCase());
    }

}