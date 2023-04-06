package com.trip.server.service;

import com.trip.server.database.entity.User;
import com.trip.server.database.entity.UserRole;
import com.trip.server.database.repository.UserRoleRepository;

import com.trip.server.exception.*;
import lombok.*;
import org.springframework.stereotype.*;

import static com.trip.server.model.UserRole.*;

@Service
@AllArgsConstructor
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRole getById(Long id) {
        return userRoleRepository.findById(id)
                .orElseThrow(UserRoleService::getNotFoundException);
    }

    public static Boolean isUser(User user) {
        return user.getRole().getId().equals(ROLE_USER.getId()) &&
                user.getRole().getName().equals(ROLE_USER.getName());
    }

    public static Boolean isAdmin(User user) {
        return user.getRole().getId().equals(ROLE_ADMIN.getId()) &&
                user.getRole().getName().equals(ROLE_ADMIN.getName());
    }

    private static NotFoundException getNotFoundException() {
        return new NotFoundException("Роль пользователя не найдена");
    }

}
