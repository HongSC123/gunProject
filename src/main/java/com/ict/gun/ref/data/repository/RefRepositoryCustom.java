package com.ict.gun.ref.data.repository;

import com.ict.gun.ref.data.dto.RefDto;

import java.util.List;

public interface RefRepositoryCustom {
    List<RefDto> findByMyRefList(String email);
}
