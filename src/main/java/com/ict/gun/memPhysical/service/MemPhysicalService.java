package com.ict.gun.memPhysical.service;

import com.ict.gun.memPhysical.data.entity.MemPhysical;
import com.ict.gun.memPhysical.repository.MemPhysicalRepository;
import com.ict.gun.member.entity.Member;
import com.ict.gun.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class MemPhysicalService {
    private final MemPhysicalRepository memPhysicalRepository;
    private final MemberRepository memberRepository;


    public void save(MemPhysical memPhysical) {
        log.info("2");
        memPhysicalRepository.save(memPhysical);
    }


    //1개 조회
    public MemPhysical findTopByMemEmailOrderByMemPhysicalDesc(String memEmail) {
        return memPhysicalRepository.findTopByMemEmailOrderByMemPhysicalDesc(memEmail);
    }

    //측정 정보 삭제
    public void memPhysicalDelete(Long memPhysical) {
        memPhysicalRepository.deleteByMemPhysical(memPhysical);
    }
    //목록 조회
    public MemPhysical findByMemEmail(String memEmail) {
        return (MemPhysical) memPhysicalRepository.findByMemEmail(memEmail);
    }


    public List<MemPhysical> findAllByMemEmail(String memEmail) {
        return memPhysicalRepository.findAllByMemEmail(memEmail);
    }
}