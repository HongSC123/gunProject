package com.ict.gun.jwt.token.repositry;

import com.ict.gun.jwt.token.entity.TokenRedis;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TokenRedisRepository extends CrudRepository<TokenRedis, String> {

    Optional<TokenRedis> findByMemEmail(String memEmail);

    Optional<TokenRedis> findByToken(String token);
}
