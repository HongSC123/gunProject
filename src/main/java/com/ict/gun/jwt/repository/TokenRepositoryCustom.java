package com.ict.gun.jwt.repository;

import com.ict.gun.jwt.entity.Token;

import java.util.List;

public interface TokenRepositoryCustom {
    List<Token> findAllValidTokenByUserId(String userName);
}
