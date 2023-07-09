package com.trip.server.mapper;

import com.trip.server.database.entity.User;
import com.trip.server.dto.user.UserDto;
import com.trip.server.util.TimeUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Mapper {

    public void init(ModelMapper modelMapper) {
        modelMapper.createTypeMap(User.class, UserDto.class)
                .setConverter(mappingContext -> {
                    var user = mappingContext.getSource();

                    return new UserDto(
                            user.getId(),
                            user.getRole().getName(),
                            TimeUtil.getFormattedZonedDateTime(user.getRegisteredAt()),
                            user.getEmail(),
                            user.getFullName()
                    );
                });
    }

}
