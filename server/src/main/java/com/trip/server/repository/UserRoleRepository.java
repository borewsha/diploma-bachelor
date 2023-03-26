package com.trip.server.repository;

import com.trip.server.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

}
