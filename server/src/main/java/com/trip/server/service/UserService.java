package com.trip.server.service;

import java.time.*;

import com.trip.server.database.entity.User;
import com.trip.server.database.repository.UserRepository;
import com.trip.server.exception.*;
import lombok.*;
import org.springframework.security.core.context.*;
import org.springframework.stereotype.*;

import static com.trip.server.model.UserRole.*;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserRoleService userRoleService;

    private final UserCredentialService userCredentialService;

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(UserService::getNotFoundException);
    }

    public User getFromSecurityContextHolder() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var principal = (String) authentication.getPrincipal();
        var userId = Long.parseLong(principal);
        return getById(userId);
    }

    public Long register(String username, String password, String fullName) {
        if (userCredentialService.findByUsername(username) != null) {
            throw new UnprocessableEntityException("Такое имя пользователя уже занято");
        }

        var roleUser = userRoleService.getById(ROLE_USER.getId());
        var user = User.builder()
                .role(roleUser)
                .fullName(fullName)
                .registeredAt(ZonedDateTime.now())
                .build();
        userRepository.save(user);
        userCredentialService.createCredential(user, username, password);

        return user.getId();
    }

    /**
     * @param user  Пользователь
     * @param other Пользователь, относительно которого сравниваются привилегии
     * @return имеет ли пользователь user более высокие привилегии, чем пользователь other
     */
    public Boolean hasHigherPrivileges(User user, User other) {
        return UserRoleService.isAdmin(user) && UserRoleService.isUser(other);
    }

    private static NotFoundException getNotFoundException() {
        return new NotFoundException("Пользователь не найден");
    }

}
