package com.trip.server.service;

import com.trip.server.entity.*;
import com.trip.server.exception.*;
import com.trip.server.model.*;
import com.trip.server.provider.*;
import com.trip.server.repository.*;
import lombok.*;
import org.springframework.stereotype.*;

@Service
@AllArgsConstructor
public class RefreshTokenService {

    private final JwtProvider jwtProvider;

    private final RefreshTokenRepository refreshTokenRepository;

    public Jwt getTokens(User user) {
        var token = refreshTokenRepository.findByUser(user);

        if (token.isPresent()) {
            return updateTokens(token.get());
        }

        var tokens = createTokens(user);
        var refreshToken = RefreshToken.builder()
            .token(tokens.getRefreshToken())
            .user(user)
            .build();
        refreshTokenRepository.save(refreshToken);

        return tokens;
    }

    public Jwt updateTokens(String token) {
        if (!jwtProvider.validateRefreshToken(token)) {
            throw getInvalidRefreshTokenException();
        }
        var refreshToken = refreshTokenRepository.findByToken(token)
            .orElseThrow(RefreshTokenService::getInvalidRefreshTokenException);
        return updateTokens(refreshToken);
    }

    private Jwt updateTokens(RefreshToken token) {
        var tokens = createTokens(token.getUser());
        token.setToken(tokens.getRefreshToken());
        refreshTokenRepository.save(token);
        return tokens;
    }

    private Jwt createTokens(User user) {
        var accessToken = jwtProvider.createAccessToken(user);
        var refreshToken = jwtProvider.createRefreshToken(user);
        return new Jwt(accessToken, refreshToken);
    }

    private static UnauthorizedException getInvalidRefreshTokenException() {
        return new UnauthorizedException("Невалидный refresh токен");
    }

}
