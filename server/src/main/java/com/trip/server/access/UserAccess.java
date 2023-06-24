package com.trip.server.access;

import com.trip.server.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
public class UserAccess {

    private final UserService userService;

    public Boolean hasRightToView(Long userId) {
        var currentUserId = userService.getCurrentUserId();
        return Objects.equals(userId, currentUserId);
    }

}
