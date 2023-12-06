package com.ict.gun.ref.service;

import com.ict.gun.ref.data.dto.RefDto;
import com.ict.gun.ref.data.repository.RefRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RefService {
    private final RefRepository refRepository;

    public List<RefDto> findByMyRefList(String email) {
        return refRepository.findByMyRefList(email);
    }
}
