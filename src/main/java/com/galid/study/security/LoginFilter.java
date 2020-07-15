package com.galid.study.security;

import com.galid.study.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class LoginFilter extends OncePerRequestFilter {
    private final String TOKEN_PREFIX = "Bearer ";
    private final UserDetailsService detailsService;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        validateExistAccessToken(authorizationHeader);

        doAuthenticate(getAccessTokenFromAuthorizationHeader(authorizationHeader));
        filterChain.doFilter(request, response);
    }

    private void validateExistAccessToken(String authorizationHeader) {
        if(authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_PREFIX))
            throw new IllegalArgumentException("Authorization Header 가 존재하지 않거나 올바르지 않은 형식입니다.");
    }

    private String getAccessTokenFromAuthorizationHeader(String authorizationHeader) {
        return authorizationHeader.substring(TOKEN_PREFIX.length());
    }

    private void doAuthenticate(String accessToken) {

    }
}
