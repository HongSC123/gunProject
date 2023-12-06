package com.ict.gun.jwt.repository;

import com.ict.gun.jwt.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long>, TokenRepositoryCustom {
    Optional<Token> findByToken(String token);
}
