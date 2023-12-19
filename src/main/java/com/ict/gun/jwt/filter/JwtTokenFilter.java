package com.ict.gun.jwt.filter;

import com.ict.gun.jwt.util.JwtTokenUtil;
import com.ict.gun.member.entity.Member;
import com.ict.gun.member.service.MemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {


    private static final int ACCESS_EXPIRED = 701;
    private static final int REFRESH_EXPIRED = 702;
    private static final int DOUBLE_EXPIRED = 703;
    private final MemberService userService;
    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain
    ) throws ServletException, IOException, ServletException, IOException {

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String refreshToken = request.getHeader("refresh");
        Boolean isAccessToken = null;
        Boolean isRefreshToken = null;

        if(authorizationHeader != null) {
            log.info("authorizationHeader : " + authorizationHeader);
            String token = authorizationHeader.split(" ")[1];
            log.info("token Expiration : " + JwtTokenUtil.isExpired(token, secretKey));
            isAccessToken = JwtTokenUtil.isExpired(token, secretKey);
            isRefreshToken = JwtTokenUtil.isExpired(refreshToken, secretKey);
             log.info("isAccessToken : " + isAccessToken);
             log.info("isRefreshToken : " + isRefreshToken);
        }
//        if(authorizationHeader == null) {
//            log.info("헤더 없음");
//        }

        if(Boolean.TRUE.equals(isAccessToken) && Boolean.TRUE.equals(isRefreshToken)) {
            response.sendError(DOUBLE_EXPIRED);
            return;
        }else if(Boolean.FALSE.equals(isAccessToken) && Boolean.TRUE.equals(isRefreshToken)) {
            response.sendError(REFRESH_EXPIRED);
            return;
        }else if(Boolean.TRUE.equals(isAccessToken) && Boolean.FALSE.equals(isRefreshToken)) {
            response.sendError(ACCESS_EXPIRED);
            return;
        }

        if(authorizationHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if(!authorizationHeader.startsWith("Bearer ") || !authorizationHeader.startsWith("Kakao ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.split(" ")[1];

        String loginId = JwtTokenUtil.getLoginId(token, secretKey);

        Member loginUser = userService.getLoginUserByLoginId(loginId);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginUser.getMemEmail(), null, List.of(new SimpleGrantedAuthority(loginUser.getRole().name())));
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
