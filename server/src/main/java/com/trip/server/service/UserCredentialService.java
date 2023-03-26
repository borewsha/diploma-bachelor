package com.trip.server.service;

import com.trip.server.entity.*;
import com.trip.server.exception.*;
import com.trip.server.model.*;
import com.trip.server.repository.*;
import lombok.*;
import org.springframework.lang.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;

@Service
@AllArgsConstructor
public class UserCredentialService {

    private PasswordEncoder passwordEncoder;

    private RefreshTokenService refreshTokenService;

    private UserCredentialRepository userCredentialRepository;

    public UserCredential getByUsername(String username) {
        return userCredentialRepository.findByUsername(username)
            .orElseThrow(UserCredentialService::getInvalidCredentialsException);
    }

    @Nullable
    public UserCredential findByUsername(String username) {
        return userCredentialRepository.findByUsername(username)
            .orElse(null);
    }

    public void addCredential(User user, String username, String password) {
        var userCredential = UserCredential.builder()
            .user(user)
            .username(username)
            .password(passwordEncoder.encode(password))
            .build();
        userCredentialRepository.save(userCredential);
    }

    public Jwt generateJwt(String username, String password) {
        var userCredential = getByUsername(username);
        if (passwordEncoder.matches(password, userCredential.getPassword())) {
            return refreshTokenService.getTokens(userCredential.getUser());
        }
        throw getInvalidCredentialsException();
    }

    private static UnauthorizedException getInvalidCredentialsException() {
        return new UnauthorizedException("Неверный логин или пароль");
    }

}
