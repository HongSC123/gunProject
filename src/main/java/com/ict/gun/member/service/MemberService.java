package com.ict.gun.member.service;

import com.ict.gun.member.dto.LoginRequest;
import com.ict.gun.member.entity.Member;
import com.ict.gun.member.entity.MemberOptions;
import com.ict.gun.member.repository.MemberOptionRepository;
import com.ict.gun.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberOptionRepository memberOptionRepository;

    public void join(Member member) {
        memberRepository.save(member);
    }

    public Boolean isDuplicate(String memEmail) {
        return memberRepository.existsById(memEmail);
    }

    public void saveOption(MemberOptions member) {
        memberOptionRepository.save(member);
    }

    // Spring Security를 사용한 로그인 구현 시 사용
    // private final BCryptPasswordEncoder encoder;

    /**
     * loginId 중복 체크
     * 회원가입 기능 구현 시 사용
     * 중복되면 true return
     */
    public boolean checkLoginIdDuplicate(String loginId) {
        return memberRepository.existsByMemEmail(loginId);
    }


    /**
     *  로그인 기능
     *  화면에서 LoginRequest(loginId, password)을 입력받아 loginId와 password가 일치하면 User return
     *  loginId가 존재하지 않거나 password가 일치하지 않으면 null return
     */
    public Member login(LoginRequest req) {
        Optional<Member> optionalUser = memberRepository.findByMemEmail(req.getMemEmail());

        // loginId와 일치하는 User가 없으면 null return
        if(optionalUser.isEmpty()) {
            return null;
        }

        Member user = optionalUser.get();

        // 찾아온 User의 password와 입력된 password가 다르면 null return
        if(!user.getMemPw().equals(req.getMemPw())) {
            return null;
        }
        return user;
    }

    /**
     * loginId(String)를 입력받아 User을 return 해주는 기능
     * 인증, 인가 시 사용
     * loginId가 null이거나(로그인 X) userId로 찾아온 User가 없으면 null return
     * loginId로 찾아온 User가 존재하면 User return
     */
    public Member getLoginUserByLoginId(String loginId) {
        if(loginId == null) return null;

        Optional<Member> optionalUser = memberRepository.findByMemEmail(loginId);
        return optionalUser.orElse(null);

    }

}
