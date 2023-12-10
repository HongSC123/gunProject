package com.ict.gun.member.controller;

import com.ict.gun.common.FileHandler;
import com.ict.gun.config.ApplicationConfig;
import com.ict.gun.jwt.token.entity.TokenRedis;
import com.ict.gun.jwt.token.repositry.TokenRedisRepository;
import com.ict.gun.jwt.util.JwtTokenUtil;
import com.ict.gun.member.entity.Member;
import com.ict.gun.member.entity.MemberOptions;
import com.ict.gun.member.repository.MemberRepository;
import com.ict.gun.member.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.ict.gun.member.controller.MemberUtil.emailToFolderName;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
public class MemberController {
    private final MemberService memberService;
    private final ApplicationConfig passwordEncoder;
    private final MemberRepository memberRepository;
    private final TokenRedisRepository tokenRedisRepository;

    @Value("${application.security.jwt.secret-key}")
    String secretKey;
    @Value("${application.security.jwt.expiration}")
    long expiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    long refreshTokenExpiration;
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody Member member) {
        try {
            // 비밀번호 암호화
            String encodedPw = passwordEncoder.passwordEncoder().encode(member.getMemPw());
            // 회원메일 중복 체크
            Boolean isDuplicate = memberService.isDuplicate(member.getMemEmail());

            if(isDuplicate) {
                String result = "duplicate";
                return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
            }
            if(!isDuplicate) {
                // 회원가입 서비스 호출
                member.setMemPw(encodedPw);
                member.setMemType("member");
                memberService.join(member);
                log.info("member" + member);
                // 성공적으로 가입되었을 때
                String result = "success";
                return ResponseEntity.ok(result);
            }
        } catch (Exception e) {
            // 가입 실패 시
            String result = "fail";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
        return null;
    }

    @PostMapping("/memoption")
    public ResponseEntity<String> memOption(@RequestParam("memGen") String memGen,
                                            @RequestParam("memWeight") float memWeight,
                                            @RequestParam("memHeight") float memHeight,
                                            @RequestParam("memBir") Date memBir,
                                            @RequestParam("memActLevel") String memActLevel,
                                            @RequestParam("memEmail") String memEmail,
                                            @RequestPart("memPhoto") MultipartFile memPhoto) {
        MemberOptions member = new MemberOptions();
        FileHandler handler = new FileHandler();
        Optional<Member> memberBase = memberRepository.findByMemEmail(memEmail);
        member.setMemGen(memGen);
        member.setMemWeight(memWeight);
        member.setMemHeight(memHeight);
        member.setMemBir(memBir);
        member.setMemActLevel(memActLevel);
        member.setMemEmail(memEmail);
        String forderName = "member/" + emailToFolderName(memEmail);
        // log.info("forderName : "+forderName);
        if(handler.handleFileUpload(memPhoto, forderName)){
            memberBase.get().setMemPhoto(forderName+"/"+memPhoto.getOriginalFilename());
        }else{
            memberBase.get().setMemPhoto("default");
        }
        log.info("member : " + member);
        memberRepository.save(memberBase.get());
        log.info("memberBase : " + memberBase);
        memberService.saveOption(member);
        String result = "success";
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody Member member) {
        Optional<Member> memberBase = memberRepository.findByMemEmail(member.getMemEmail());
        if(memberBase.isEmpty()){
            String result = "fail";
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        String Token = JwtTokenUtil.createToken(member.getMemEmail(), secretKey, expiration);
        String RefreshToken = JwtTokenUtil.createRefreshToken(secretKey, refreshTokenExpiration);
        // log.info("Token : " + Token);
        Map<String,String> result = new HashMap<>();
        result.put("accessToken", Token);
        result.put("refreshToken", RefreshToken);
        TokenRedis tokenRedis = new TokenRedis(member.getMemEmail(), Token, RefreshToken, expiration, refreshTokenExpiration);
        log.info("tokenRedis : " + tokenRedis.toString());
        tokenRedisRepository.save(tokenRedis);
        Optional<TokenRedis> token = tokenRedisRepository.findById(tokenRedis.getId());
        log.info("token : " + token.get().toString());

        // test 삭제
        //tokenRedisRepository.deleteAll();
        return ResponseEntity.ok().body(result);
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody String accessToken) {
        log.info("accessToken : " + accessToken);
        return ResponseEntity.ok("success");
    }
}
