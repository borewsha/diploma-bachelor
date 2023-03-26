package com.trip.server.repository;

import java.util.*;

import com.trip.server.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByUser(User user);

    Optional<RefreshToken> findByToken(String token);

}
