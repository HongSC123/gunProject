package com.ict.gun.member.service;

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
public class MemberService {
    private final MemberRepository memberRepository;


    public void join(Member member) {
        memberRepository.save(member);
    }

    public Boolean isDuplicate(String memEmail) {
        return memberRepository.existsById(memEmail);
    }
}
