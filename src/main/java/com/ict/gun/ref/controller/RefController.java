package com.ict.gun.ref.controller;

import com.ict.gun.member.entity.Member;
import com.ict.gun.member.entity.MemberCommand;
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
public class RefController {


    @PostMapping("/ref")
    public String signUp(@RequestBody MemberCommand memberCommand) throws Exception {

        return "d";
    }
}
