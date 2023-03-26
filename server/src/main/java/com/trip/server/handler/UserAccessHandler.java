package com.trip.server.handler;

import com.trip.server.service.*;
import lombok.*;
import org.springframework.stereotype.*;

@Component
@AllArgsConstructor
public class UserAccessHandler {

    private final UserService userService;

    public Boolean hasAccess(Long userId) {
        var authenticatedUser = userService.getFromSecurityContextHolder();

        if (authenticatedUser.getId().equals(userId)) {
            return true;
        }

        var user = userService.getById(userId);

        return userService.hasHigherPrivileges(authenticatedUser, user);
    }

}
