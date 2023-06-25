package com.trip.server.provider;

import java.security.*;
import java.util.*;

import javax.crypto.*;

import com.trip.server.database.entity.User;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.*;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.security.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.*;

@Slf4j
@Component
public class JwtProvider {

    @Value("${jwt.access.session.time-millis}")
    private Long accessSessionTimeMillis;

    @Value("${jwt.refresh.session.time-millis}")
    private Long refreshSessionTimeMillis;

    private final SecretKey jwtAccessSecret;

    private final SecretKey jwtRefreshSecret;

    public JwtProvider(
        @Value("${jwt.access.secret}") String jwtAccessSecret,
        @Value("${jwt.refresh.secret}") String jwtRefreshSecret
    ) {
        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
        this.jwtRefreshSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret));
    }

    public String createAccessToken(User user) {
        var currentTimeMillis = System.currentTimeMillis();
        return Jwts.builder()
            .setSubject(String.valueOf(user.getId()))
            .setExpiration(new Date(currentTimeMillis + accessSessionTimeMillis))
            .setIssuedAt(new Date(currentTimeMillis))
            .signWith(jwtAccessSecret)
            .claim("role", user.getRole().getName())
            .compact();
    }

    public String createRefreshToken(User user) {
        var currentTimeMillis = System.currentTimeMillis();
        return Jwts.builder()
            .setSubject(String.valueOf(user.getId()))
            .setExpiration(new Date(currentTimeMillis + refreshSessionTimeMillis))
            .signWith(jwtRefreshSecret)
            .compact();
    }

    public boolean validateAccessToken(@NonNull String accessToken) {
        return validateToken(accessToken, jwtAccessSecret);
    }

    public boolean validateRefreshToken(@NonNull String refreshToken) {
        return validateToken(refreshToken, jwtRefreshSecret);
    }

    private boolean validateToken(@NonNull String token, @NonNull Key secret) {
        try {
            var subject = Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
            Long.parseLong(subject);
            return true;
        } catch (ExpiredJwtException ignored) {
            log.debug("Токен истек");
        } catch (UnsupportedJwtException ignored) {
            log.debug("Неподдерживаемый токен");
        } catch (MalformedJwtException ignored) {
            log.debug("Плохо сформированный токен");
        } catch (SignatureException ignored) {
            log.debug("Невалидная сигнатура");
        } catch (Exception e) {
            log.debug("Невалидный токен", e);
        }
        return false;
    }

    public Claims getAccessClaims(@NonNull String token) {
        return getClaims(token, jwtAccessSecret);
    }

    public Claims getRefreshClaims(@NonNull String token) {
        return getClaims(token, jwtRefreshSecret);
    }

    private Claims getClaims(@NonNull String token, @NonNull Key secret) {
        return Jwts.parserBuilder()
            .setSigningKey(secret)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

}
