package com.trip.server.database.repository;

import com.trip.server.database.entity.UserRole;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

}
