package com.trip.server.controller;

import java.util.*;

import org.modelmapper.*;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
public class ApiController {

    protected static <T, D> List<D> mapToDtoList(ModelMapper mapper, List<T> objects, Class<D> dtoClass) {
        return objects.stream()
            .map(o -> mapper.map(o, dtoClass))
            .toList();
    }

}
