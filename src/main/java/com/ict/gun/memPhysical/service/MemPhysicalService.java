package com.ict.gun.memPhysical.service;

import com.ict.gun.memPhysical.data.entity.MemPhysical;
import com.ict.gun.memPhysical.repository.MemPhysicalRepository;
import com.ict.gun.member.entity.Member;
import com.ict.gun.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public MemPhysical findByMemEmail (String email){
        Member member = memberRepository.findByMemEmail(email);
        return memPhysicalRepository.findByMemEmail(member);
        }

        //측정 정보 삭제
    public void memPhysicalDelete (String email){
    Member member = memberRepository.findByMemEmail(email);
    memPhysicalRepository.deleteByMemEmail(member);
    }

    //public MemPhysical create(MemPhysicalRepository memPhysicalRepository) {
    //    MemPhysical entity = MemPhysical.builder()
    //            .memPhysical(memPhysicalRepository.getMemPhysical())
    //            .memLocation(memPhysicalRepository.getMemLocation())
    //            .memPoint(memPhysicalRepository.getMemPoint())
    //            .memCount(memPhysicalRepository.getMemCount())
    //            .memInputDate(memPhysicalRepository.getMemInputDate())
    //            .build();
    //    return memPhysicalRepository.save(entity);
    //}
    }

