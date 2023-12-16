package com.ict.gun.config;

import com.ict.gun.jwt.filter.JwtTokenFilter;
import com.ict.gun.jwt.token.entity.TokenRedis;
import com.ict.gun.jwt.token.repositry.TokenRedisRepository;
import com.ict.gun.member.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.ict.gun.member.entity.UserRole;
import java.util.Optional;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {
    private final MemberService userService;
    @Value("${application.security.jwt.secret-key}")
    String secretKey;
    private final TokenRedisRepository tokenRedisRepository;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtTokenFilter(userService, secretKey), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()

                .requestMatchers("/loader.css","/assets/**","/","/index.html", "/signup", "/css/**", "/js/**", "/images/**", "/favicon.ico", "/login","/newtoken","/loginkakao","/memoption","/changePassword","/profile","/admin/**","/login/face","/memoption2","/notice/**").permitAll()
                .requestMatchers("/caloriedaylist/**", "/caloriedetail/**", "/caloriedaycalorie/**", "/calorietoday/**", "/calorieinsert","/ref","/ref/**").permitAll()
                .requestMatchers("/chatinsert", "/chatrecent", "/chatcount", "/chatycount", "/chatupdate/**", "/recipe/chatdetail").authenticated()
                .requestMatchers("/memPhysical/insert","/detail/physical").permitAll()
                .requestMatchers("/tokencheck","/admin/update/**").hasAuthority(UserRole.ADMIN.name())
                .requestMatchers("/memPhysical/insert","/detail/physical","/memPhysical/delete/{MEM_PHYSICAL}","/list/physical").permitAll()
                .requestMatchers("/tokencheck").hasAuthority(UserRole.ADMIN.name())
                .anyRequest().authenticated()
                .and()
                .logout()
                // 로그아웃을 수행할 엔드포인트 지정
                .logoutUrl("/logout")
                // HTTP 세션 무효화
                .invalidateHttpSession(true)
                // 사용자 인증 정보 삭제
                .clearAuthentication(true)
                // 로그아웃 성공 핸들러 설정
                .logoutSuccessHandler((request, response, authentication) -> {
                    // 로그아웃 성공 후의 사용자 정의 로직 추가 가능
                    String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
                    String refreshToken = request.getHeader("refresh");
                    // log.info("token : " + authorizationHeader);
                    if(authorizationHeader != null) {
                        String token = authorizationHeader.split(" ")[1];
                        log.info("logout token : " + token);
                        log.info("refreshToken : " + refreshToken);
                        try {
                            Claims claims = Jwts.parserBuilder()
                                    .setSigningKey(secretKey) // Replace with your actual secret key
                                    .build()
                                    .parseClaimsJws(token)
                                    .getBody();

                            String userId = (String) claims.get("loginId");
                            // log.info("UserID: " + userId);
                            // Optional<TokenRedis> tokenRedis = tokenRedisRepository.findById(userId);
                            // log.info("tokenRedis : " + tokenRedis.get().toString());
                            tokenRedisRepository.deleteById(userId);

                        } catch (Exception e) {
                            log.error("Error parsing JWT: " + e.getMessage());
                        }

                    }
                    response.setStatus(HttpServletResponse.SC_OK);
                })
                .and()
                // 예외 처리 설정
                .exceptionHandling()
                // 사용자 인증 진입 지점 설정
                .authenticationEntryPoint((request, response, authException) -> {
                    // 사용자 정의 인증 진입 지점 로직 추가 가능
                    // response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                });


        return httpSecurity.build();
    }

}
