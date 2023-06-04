package com.trip.server.database.repository;

import com.trip.server.database.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    List<Place> findByOsmIdIn(Collection<String> osmIds);

}
