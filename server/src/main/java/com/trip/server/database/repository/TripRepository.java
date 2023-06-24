package com.trip.server.database.repository;

import com.trip.server.database.entity.Trip;
import com.trip.server.database.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    Page<Trip> findByUser(User user, Pageable pageable);

}
