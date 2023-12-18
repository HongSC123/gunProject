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
                .cors().and()
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtTokenFilter(userService, secretKey), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .requestMatchers("/loader.css","/assets/**","/","/index.html", "/signup", "/css/**", "/js/**", "/images/**", "/favicon.ico", "/login","/newtoken","/loginkakao","/memoption","/changePassword","/profile","/admin/**","/login/face","/memoption2","/notice/**","/loginnaver","/email/{email}","/emailok").permitAll()
                .requestMatchers("/caloriedaylist/**", "/caloriedetail/**", "/caloriedaycalorie/**", "/calorietoday/**", "/calorieinsert","/ref","/ref/**").permitAll()
                .requestMatchers("/chatinsert", "/chatrecent", "/chatcount", "/chatycount", "/chatupdate/**", "/recipe/chatdetail").authenticated()
                .requestMatchers("/memPhysical/insert","/detail/physical").permitAll()
                .requestMatchers("/tokencheck","/admin/update/**").hasAuthority(UserRole.ADMIN.name())
                .requestMatchers("/memPhysical/insert","/detail/physical","/memPhysical/delete/{MEM_PHYSICAL}","/list/physical").permitAll()
                .requestMatchers("/tokencheck").hasAuthority(UserRole.ADMIN.name())
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutSuccessHandler((request, response, authentication) -> {
                    String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
                    String refreshToken = request.getHeader("refresh");
                    if(authorizationHeader != null) {
                        String token = authorizationHeader.split(" ")[1];
                        log.info("logout token : " + token);
                        log.info("refreshToken : " + refreshToken);
                        try {
                            Claims claims = Jwts.parserBuilder()
                                    .setSigningKey(secretKey)
                                    .build()
                                    .parseClaimsJws(token)
                                    .getBody();
                            String userId = (String) claims.get("loginId");
                            tokenRedisRepository.deleteById(userId);
                        } catch (Exception e) {
                            log.error("Error parsing JWT: " + e.getMessage());
                        }
                    }
                    response.setStatus(HttpServletResponse.SC_OK);
                })
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                });
        return httpSecurity.build();
    }

}
