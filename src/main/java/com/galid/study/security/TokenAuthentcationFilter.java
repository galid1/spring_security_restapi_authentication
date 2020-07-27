package com.galid.study.security;

import com.galid.study.config.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class TokenAuthentcationFilter extends OncePerRequestFilter {
    private final String TOKEN_PREFIX = "Bearer ";
    private final UserDetailsService detailsService;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        validateAuthorizationHeader(authorizationHeader);
        doAuthenticate(getAccessTokenFromAuthorizationHeader(authorizationHeader));

        filterChain.doFilter(request, response);
    }

    private void validateAuthorizationHeader(String authorizationHeader) {
        if(authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_PREFIX))
            throw new IllegalArgumentException("Authorization Header 가 존재하지 않거나 올바르지 않은 형식입니다.");
    }

    private void doAuthenticate(String accessToken) {
        // token 유효성 검사
        jwtUtil.validateToken(accessToken);

        String userAuthId = jwtUtil.getClaimFromToken(accessToken, Claims::getAudience);
        UserDetails userDetails = detailsService.loadUserByUsername(userAuthId);

        makeAuthenticated(new UsernamePasswordAuthenticationToken(userDetails, accessToken, userDetails.getAuthorities()));
    }

    private String getAccessTokenFromAuthorizationHeader(String authorizationHeader) {
        return authorizationHeader.substring(TOKEN_PREFIX.length());
    }

    private void makeAuthenticated(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
