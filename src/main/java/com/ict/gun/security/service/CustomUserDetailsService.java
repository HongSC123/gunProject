package com.ict.gun.security.service;

import com.ict.gun.member.entity.Member;
import com.ict.gun.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String memEmail) throws UsernameNotFoundException {
        log.info("loadUserByUsername ....");
        Optional<Member> member = memberRepository.findByMemEmail(memEmail);
        if (member.isEmpty()) return null;
        List<String> roles = new ArrayList<>();
        roles.add("MEMBER");
        return org.springframework.security.core.userdetails.User.builder()
                .username(member.get().getMemEmail())
                .password(member.get().getMemPw())
                .roles(roles.toArray(new String[0]))
                .build();
    }

    public String userId(String memEmail) {
        Optional<Member> member = memberRepository.findByMemEmail(memEmail);
        return member.map(value -> value.getMemEmail().toString()).orElse(null);
    }
}
