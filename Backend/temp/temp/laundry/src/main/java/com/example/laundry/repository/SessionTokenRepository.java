package com.example.laundry.repository;


import com.example.laundry.entities.SessionToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionTokenRepository extends JpaRepository<SessionToken, Integer> {

    Optional<SessionToken> findByToken(String token);
}
