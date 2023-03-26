package com.trip.server.mapper;

import com.trip.server.dto.*;
import com.trip.server.model.*;
import org.modelmapper.*;
import org.springframework.stereotype.*;

@Component
public class JwtMapper implements Mapper {

    public void init(ModelMapper modelMapper) {
        modelMapper.createTypeMap(Jwt.class, JwtDto.class)
            .setConverter(mappingContext -> {
                var jwt = mappingContext.getSource();
                return new JwtDto(
                    jwt.getAccessToken(),
                    jwt.getRefreshToken()
                );
            });
    }

}
