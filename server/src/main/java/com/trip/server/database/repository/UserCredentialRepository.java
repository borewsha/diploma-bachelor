package com.trip.server.database.repository;

import java.util.*;

import com.trip.server.database.entity.UserCredential;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, Long> {

    Optional<UserCredential> findByUsername(String username);

}
