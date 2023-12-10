package com.ict.gun.config;

import com.ict.gun.jwt.filter.JwtTokenFilter;
import com.ict.gun.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final MemberService userService;
    @Value("${application.security.jwt.secret-key}")
    String secretKey;

@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity

                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtTokenFilter(userService, secretKey), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .requestMatchers("/loader.css","/assets/**","/","/index.html", "/signup", "/css/**", "/js/**", "/images/**", "/favicon.ico", "/login").permitAll()
                .anyRequest().authenticated();

        return httpSecurity.build();
    }

}
