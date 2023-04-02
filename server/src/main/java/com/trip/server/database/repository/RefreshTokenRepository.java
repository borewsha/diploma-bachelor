package com.trip.server.database.repository;

import java.util.*;

import com.trip.server.database.entity.RefreshToken;
import com.trip.server.database.entity.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByUser(User user);

    Optional<RefreshToken> findByToken(String token);

}
