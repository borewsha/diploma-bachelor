package com.trip.server.database.repository;

import com.trip.server.database.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    Optional<City> findByOsmId(String osmId);

    List<City> findByOsmIdIn(Collection<String> osmIds);

}
