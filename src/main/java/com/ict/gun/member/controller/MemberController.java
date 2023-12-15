package com.ict.gun.member.controller;

import com.ict.gun.common.FileHandler;
import com.ict.gun.config.ApplicationConfig;
import com.ict.gun.jwt.token.entity.TokenRedis;
import com.ict.gun.jwt.token.repositry.TokenRedisRepository;
import com.ict.gun.jwt.util.JwtTokenUtil;
import com.ict.gun.member.entity.Member;
import com.ict.gun.member.entity.MemberOptions;
import com.ict.gun.member.entity.UserRole;
import com.ict.gun.member.repository.MemberOptionRepository;
import com.ict.gun.member.repository.MemberRepository;
import com.ict.gun.member.service.MemberService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.Date;
import java.util.*;

import static com.ict.gun.member.controller.MemberUtil.decodeToken;
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
    private final MemberOptionRepository memberOptionRepository;

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
                member.setMemType(member.getRole().name());
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
                                            @RequestParam("memActLevel") Float memActLevel,
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
        log.info("member : " + member);
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
        log.info("member : " + member);
        Optional<Member> memberBase = memberRepository.findByMemEmail(member.getMemEmail());
        Map<String,String> result = new HashMap<>();
        if(memberBase.isEmpty()){
            result.put("error", "801");
        }
        if(memberBase.get().getRole().name().equals(UserRole.ADMIN.name())){
            result.put("error", "803");
        }
        if(!passwordEncoder.passwordEncoder().matches(member.getMemPw(), memberBase.get().getMemPw())){
            result.put("error", "802");
        }else{
            String Token = JwtTokenUtil.createToken(member.getMemEmail(), secretKey, expiration);
            String RefreshToken = JwtTokenUtil.createRefreshToken(member.getMemEmail(),secretKey, refreshTokenExpiration);
            result.put("accessToken", Token);
            result.put("refreshToken", RefreshToken);
            result.put("role", memberBase.get().getRole().toString());
            TokenRedis tokenRedis = new TokenRedis(member.getMemEmail(), Token, RefreshToken, expiration, refreshTokenExpiration);
            tokenRedisRepository.save(tokenRedis);
            Optional<TokenRedis> token = tokenRedisRepository.findById(tokenRedis.getId());
        }
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/tokencheck")
    public ResponseEntity<String> tokenCheck(HttpServletRequest request) {
        String result  = "success";
        return ResponseEntity.ok(result);
    }

    @PostMapping("/newtoken")
    public ResponseEntity<Map<String,String>> newToken(@RequestBody Map<String, Object> errorToken) {

        String refresh = (String) errorToken.get("refresh");
        String access = (String) errorToken.get("access");
        int error = (int) errorToken.get("error");

        Boolean isAccessTokenIssued = true;
        Boolean isRefreshTokenIssued = true;

        log.info("*** accessToken : " + access);
        log.info("*** refreshToken : " + refresh);

        Map<String,String> result = new HashMap<>();

        if(error == 701){
            log.info("ACCESS_EXPIRED");
            Claims refreshToken = decodeToken(refresh, secretKey);
            String memEmail = refreshToken.get("loginId", String.class);
            log.info("memEmail : " + memEmail);
            access = JwtTokenUtil.createToken(memEmail, secretKey, expiration);
            TokenRedis newAccessToken = new TokenRedis(memEmail, access, refresh, expiration, refreshTokenExpiration);

            Optional<TokenRedis> token = tokenRedisRepository.findById(memEmail);
            if(!token.get().getRefreshToken().equals(refresh)){
                isRefreshTokenIssued = false;
            }
            if(isRefreshTokenIssued){
                tokenRedisRepository.deleteById(memEmail);
                tokenRedisRepository.save(newAccessToken);
                log.info("*** accessToken : " + access);
            }
        }
        else if(error == 702){
            log.info("REFRESH_EXPIRED");
            Claims accessToken = decodeToken(access, secretKey);
            String memEmail = accessToken.get("loginId", String.class);
            log.info("memEmail : " + memEmail);
            refresh = JwtTokenUtil.createRefreshToken(memEmail, secretKey, refreshTokenExpiration);
            TokenRedis newRefreshToken = new TokenRedis(memEmail, access, refresh, expiration, refreshTokenExpiration);
            Optional<TokenRedis> token = tokenRedisRepository.findById(memEmail);
            if(!token.get().getToken().equals(access)){
                isAccessTokenIssued = false;
            }
            if(isAccessTokenIssued){
                tokenRedisRepository.deleteById(memEmail);
                tokenRedisRepository.save(newRefreshToken);
                log.info("*** refreshToken : " + refresh);
            }
        }
        if(isAccessTokenIssued && isRefreshTokenIssued){
            result.put("accessToken", access);
            result.put("refreshToken", refresh);
        }else {
            if(!isAccessTokenIssued){
                result.put("error", "Not a Access token we issued");
            }
            if(!isRefreshTokenIssued){
                result.put("error", "Not a Refresh token we issued");
            }
        }
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/loginkakao")
    public ResponseEntity<Map<String,String>> loginKakao(@RequestBody Map<String, String> kakaoInfo) {
        String accessToken = kakaoInfo.get("accessToken");
        String refreshToken = kakaoInfo.get("refreshToken");
        String memEmail = kakaoInfo.get("memEmail");
        Member kakaoMember = new Member();
        kakaoMember.setMemEmail(memEmail);
        kakaoMember.setMemType("KAKAO");
        kakaoMember.setRole(UserRole.USER);
        kakaoMember.setMemPw(memEmail);
        if (memberRepository.findById(memEmail).get().getMemEn() != null) {
            kakaoMember.setMemMod(new Date(System.currentTimeMillis()));
        } else {
            kakaoMember.setMemEn(new Date(System.currentTimeMillis()));
        }
        kakaoMember.setMemAct("Y");

        tokenRedisRepository.save(new TokenRedis(memEmail, accessToken, refreshToken, expiration, refreshTokenExpiration));
        memberService.join(kakaoMember);

        log.info("memEmail : " + memEmail);
        Map<String,String> result = new HashMap<>();

        result.put("accessToken", accessToken);
        result.put("refreshToken", refreshToken);
        result.put("role", UserRole.USER.name());

        return ResponseEntity.ok(result);
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String loginType = request.getHeader("loginType");
        if(loginType.equals("KAKAO")){
            String memEmail = request.getHeader("memEmail");
            tokenRedisRepository.deleteById(memEmail);
        }
        else if(loginType.equals("MEMBER")){
            Claims accessToken = decodeToken(request.getHeader(HttpHeaders.AUTHORIZATION).split(" ")[1], secretKey);
            String memEmail = accessToken.get("loginId", String.class);
            tokenRedisRepository.deleteById(memEmail);
        }
        // Claims accessToken = decodeToken(request.getHeader(HttpHeaders.AUTHORIZATION).split(" ")[1], secretKey);
        return ResponseEntity.ok("success");
    }

    @GetMapping("/profile")
    public ResponseEntity<Map<String,Object>> profile(HttpServletRequest request) {
        if(request.getHeader("loginType").equals("KAKAO")){
            String memEmail = request.getHeader("memEmail");
            return ResponseEntity.ok().body(getProfile(memEmail));
        }else{
            return ResponseEntity.ok().body(getProfile(decodeToken(request.getHeader(HttpHeaders.AUTHORIZATION).split(" ")[1], secretKey).get("loginId", String.class)));
        }
    }

    private Map<String,Object> getProfile(String memEmail){
        Optional<Member> member = memberRepository.findById(memEmail);
        MemberOptions memberOptions = new MemberOptions();
        try{
            memberOptions = memberOptionRepository.findById(memEmail).get();
        }catch (Exception e){
            memberOptions = new MemberOptions();
        }

        Map<String,Object> result = new HashMap<>();

        result.put("memEmail", memEmail);
        result.put("memPhoto", member.get().getMemPhoto());
        result.put("memGen", memberOptions.getMemGen());
        result.put("memWeight", memberOptions.getMemWeight());
        result.put("memHeight", memberOptions.getMemHeight());
        result.put("memBir", memberOptions.getMemBir());
        result.put("memActLevel", memberOptions.getMemActLevel());

        return result;
    }

    @PostMapping("/changePassword")
    private ResponseEntity<Map<String, String>> changePassword(HttpServletRequest request, @RequestBody Map<String, String> password){
        if(request.getHeader("loginType").equals("KAKAO")){
            String memEmail = request.getHeader("memEmail");
            memberRepository.findById(memEmail).get().setMemPw(password.get("newPassword"));
            return ResponseEntity.ok().body(null);
        }
        Claims claims = decodeToken(request.getHeader(HttpHeaders.AUTHORIZATION).split(" ")[1], secretKey);
        String memEmail = claims.get("loginId", String.class);
        Member member = memberRepository.findById(memEmail).get();
        if(passwordEncoder.matches(password.get("currentPassword"), member.getMemPw())){
            member.setMemPw(passwordEncoder.passwordEncoder().encode(password.get("newPassword")));
            memberRepository.save(member);
            Map<String, String> result = new HashMap<>();
            result.put("result", "success");
            return ResponseEntity.ok(result);
        }else{
            Map<String, String> result = new HashMap<>();
            result.put("error","기존 비밀번호가 다릅니다.");
            return ResponseEntity.ok().body(result);
        }
    }
    @GetMapping("/admin/list")
    public List<Member> adminList(){
        return memberRepository.findByRoleNot(UserRole.ADMIN);
    }
    @PostMapping("/login/face")
    public ResponseEntity<Map<String,String>> loginFace(@RequestBody Map<String, String> base64Image,HttpServletRequest request) {
        String base64ImageStr = base64Image.get("image");
        String memEmail = base64Image.get("email");
        String photoEmail = "";
        Map<String,String> result = new HashMap<>();
        log.info("memEmail : " + memEmail);
        byte[] imageBytes = Base64.getDecoder().decode(base64ImageStr);
        String filePath = "E:/gun_workspace/gun/src/main/resources/faceLoginInput/faceLoginInput.jpg";
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
            result.put("result", "fail");
            return ResponseEntity.ok().body(result);
        }

        try {
            ProcessBuilder processBuilder = new ProcessBuilder( "E:/gun_workspace/gun/src/main/resources/faceModel/faceLoginCheck.exe","E:/gun_workspace/gun/src/main/resources/faceLoginInput/faceLoginInput.jpg");
            processBuilder.directory(new File("E:/gun_workspace/gun/src/main/resources/faceModel/"));
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while((line = reader.readLine()) != null) {
                photoEmail += line;
            }
            int exitCode = process.waitFor();
            log.info("전송완");
            if(memEmail.equals(photoEmail)){
                Optional<Member> member = memberRepository.findById(memEmail);
                log.info("로그인 성공");
                String Token = JwtTokenUtil.createToken(member.get().getMemEmail(), secretKey, expiration);
                String RefreshToken = JwtTokenUtil.createRefreshToken(member.get().getMemEmail(),secretKey, refreshTokenExpiration);
                result.put("accessToken", Token);
                result.put("refreshToken", RefreshToken);
                result.put("role", member.get().getRole().toString());
                TokenRedis tokenRedis = new TokenRedis(member.get().getMemEmail(), Token, RefreshToken, expiration, refreshTokenExpiration);
                tokenRedisRepository.save(tokenRedis);
                Optional<TokenRedis> token = tokenRedisRepository.findById(tokenRedis.getId());
                result.put("result", "success");
                return ResponseEntity.ok(result);
            }else if(!memEmail.equals(photoEmail)){
                result.put("result", "fail");
                return ResponseEntity.ok(result);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }
}
