package com.ict.gun;

import com.ict.gun.jwt.token.entity.TokenRedis;
import com.ict.gun.jwt.token.repositry.TokenRedisRepository;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@Slf4j
@SpringBootTest
public class RedisRepositoryTest {

    @Autowired
    TokenRedisRepository repo;


    @Test
    void test(){
        TokenRedis token = new TokenRedis("test@naver.com", "t123123easdfasfds123123t", "tadsfasdfeqwerqwrest", 1000);
        log.info("***token : " + token.toString());
        repo.save(token);
        Optional<TokenRedis> result = repo.findById(token.getId());
        log.info("***result : " + result.get().toString());
        repo.deleteAll();

    }
}
