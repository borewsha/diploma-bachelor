package com.trip.server.model;

import java.util.*;

import lombok.*;
import org.springframework.security.core.*;

@Getter
@Setter
public class JwtAuthentication implements Authentication {

    private boolean authenticated;

    private String username;

    private String name;

    private UserRole role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(role);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

}
