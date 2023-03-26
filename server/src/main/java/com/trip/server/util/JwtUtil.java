package com.trip.server.util;

import com.trip.server.model.*;
import io.jsonwebtoken.*;
import lombok.experimental.*;

@UtilityClass
public final class JwtUtil {

    public static JwtAuthentication generate(Claims claims) {
        var jwtAuthentication = new JwtAuthentication();
        jwtAuthentication.setRole(getRole(claims));
        jwtAuthentication.setUsername(claims.getSubject());
        return jwtAuthentication;
    }

    private static UserRole getRole(Claims claims) {
        var role = claims.get("role", String.class);
        return UserRole.valueOf(role);
    }

}
