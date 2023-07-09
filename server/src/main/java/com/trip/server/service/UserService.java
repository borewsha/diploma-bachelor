package com.trip.server.service;

import java.time.*;

import com.trip.server.database.entity.User;
import com.trip.server.database.repository.UserRepository;
import com.trip.server.exception.*;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<User> getAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Long getCurrentUserId() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var principal = (String) authentication.getPrincipal();

        if (principal.equals("anonymousUser")) {
            throw new UnauthorizedException("Вход не выполнен");
        }

        return Long.parseLong(principal);
    }

    public User getCurrentUser() {
        return getById(getCurrentUserId());
    }

    public User register(String email, String password, String fullName) {
        if (userCredentialService.findByUsername(email) != null) {
            throw new UnprocessableEntityException("Такое имя пользователя уже занято");
        }

        var roleUser = userRoleService.getById(ROLE_USER.getId());
        var user = User.builder()
                .role(roleUser)
                .email(email)
                .fullName(fullName)
                .registeredAt(ZonedDateTime.now())
                .build();
        userRepository.save(user);
        userCredentialService.createCredential(user, email, password);

        return user;
    }

    private static NotFoundException getNotFoundException() {
        return new NotFoundException("Пользователь не найден");
    }

}
