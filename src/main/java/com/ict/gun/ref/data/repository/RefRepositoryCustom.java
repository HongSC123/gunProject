package com.ict.gun.ref.data.repository;

import com.ict.gun.ref.data.dto.RefDto;

import java.util.List;

public interface RefRepositoryCustom {
    List<RefDto> findByMyRefList(String email);
    Long findRefCode();
    void deleteAllByREF_CODE(Long REF_CODE);
    long findByRefCode(Long refCode);
    long findPhotoByRefCode(Long refCode);
}
