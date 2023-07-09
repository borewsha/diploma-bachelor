package com.trip.server.service;

import com.trip.server.database.entity.Trip;
import com.trip.server.database.entity.User;
import com.trip.server.database.repository.TripRepository;
import com.trip.server.exception.NotFoundException;
import com.trip.server.exception.UnprocessableEntityException;
import com.trip.server.model.TripCreationModel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TripService {

    private final TripRepository tripRepository;

    public Trip getById(Long id) {
        return tripRepository.findById(id)
                .orElseThrow(TripService::getNotFoundException);
    }

    public Page<Trip> getByUser(User user, Pageable pageable) {
        return tripRepository.findByUser(user, pageable);
    }

    public Trip create(TripCreationModel tripCreationModel) {
        var areCitiesMatch = tripCreationModel.getPlacesToVisit().stream()
                .allMatch(a -> a.getCity().equals(tripCreationModel.getCity()));

        if (!areCitiesMatch) {
            throw new UnprocessableEntityException("Не все места находятся в введенном городе");
        }

        var trip = Trip.builder()
                .user(tripCreationModel.getUser())
                .city(tripCreationModel.getCity())
                .accommodation(tripCreationModel.getAccommodation())
                .startsAt(tripCreationModel.getStartsAt())
                .endsAt(tripCreationModel.getEndsAt())
                .build();

        return tripRepository.save(trip);
    }

    public Page<Trip> getAll(Pageable pageable) {
        return tripRepository.findAll(pageable);
    }

    private static NotFoundException getNotFoundException() {
        return new NotFoundException("Путешествие не найдено");
    }

}
