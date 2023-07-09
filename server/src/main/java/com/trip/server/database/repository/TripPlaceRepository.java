package com.trip.server.database.repository;

import com.trip.server.database.entity.Trip;
import com.trip.server.database.entity.TripPlace;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripPlaceRepository extends JpaRepository<TripPlace, Long> {

    List<TripPlace> findByTrip(Trip trip, Sort sort);

}
