package com.trip.server.database.repository;

import com.trip.server.database.entity.User;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
