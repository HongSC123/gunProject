package com.ict.gun.member.controller;

import com.ict.gun.security.config.SpringSecurityConfig;
import com.ict.gun.member.entity.Member;
import com.ict.gun.member.entity.MemberCommand;
import com.ict.gun.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
public class MemberController {
    private final MemberService memberService;
    private final SpringSecurityConfig passwordEncoder;
    @PostMapping("/signup")
    public String signUp(@RequestBody MemberCommand memberCommand) throws Exception {
        // 비밀번호 암호화
        String encodedPw = passwordEncoder.passwordEncoder().encode(memberCommand.getMemPw());
        // vue 에 보내줄 String 작성
        String result = (memberCommand != null) ? "success" : "fail";

        // 데이터 나눠서 담음
        Member member = new Member();
        member.setMemType("member");
        member.setMemEmail(memberCommand.getMemEmail());
        member.setMemPw(encodedPw);

        memberService.join(member);
        return result;
    }
}
