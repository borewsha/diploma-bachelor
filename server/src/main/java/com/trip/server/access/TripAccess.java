package com.trip.server.access;

import com.trip.server.service.TripService;
import com.trip.server.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
public class TripAccess {

    private final UserService userService;

    private final TripService tripService;

    public Boolean hasRightToCreate(Long userId) {
        var currentUserId = userService.getCurrentUserId();
        return Objects.equals(userId, currentUserId);
    }

    public Boolean hasRightToView(Long tripId) {
        var currentUser = userService.getCurrentUser();
        var trip = tripService.getById(tripId);
        return Objects.equals(currentUser, trip.getUser());
    }

}
