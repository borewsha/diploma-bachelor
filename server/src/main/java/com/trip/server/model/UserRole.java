package com.trip.server.model;

import lombok.*;
import org.springframework.security.core.*;

@Getter
@RequiredArgsConstructor
public enum UserRole implements GrantedAuthority {

    ROLE_USER(1L, "ROLE_USER"),
    ROLE_ADMIN(2L, "ROLE_ADMIN");

    private final Long id;

    private final String name;

    @Override
    public String getAuthority() {
        return name;
    }

}
