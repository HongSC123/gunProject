package com.ict.gun.ref.data.repository;

import com.ict.gun.ref.data.dto.RefDto;
import com.ict.gun.ref.data.entity.RefEx;

import java.util.List;

public interface RefRepositoryCustom {
    List<RefDto> findByMyRefList(String email);
    int findByRefExCode(String refExCode);
    String findRefCode();
    void deleteAllByREF_CODE(String REF_CODE);

    long findByRefCode(String refCode);

    long findPhotoByRefCode(String refCode);
}
