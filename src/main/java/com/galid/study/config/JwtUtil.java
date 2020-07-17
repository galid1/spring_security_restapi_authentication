package com.galid.study.config;

import com.galid.study.user.ApiTokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
    private int ACCESS_TOKEN_EXPIRATION_DATE = 60 * 60 * 3; // 3 hours
    private int REFRESH_TOKEN_EXPIRATION_DATE = 60 * 60 * 24 * 365; // 365 Days
    private SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(String userAuthId, ApiTokenType type) {
        Date expiration;
        if(type == ApiTokenType.ACCESS_TOKEN)
            expiration = new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_DATE * 1000);
        else
            expiration = new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_DATE * 1000);

        return Jwts.builder()
                .setExpiration(expiration)
                .setAudience(userAuthId)
                .signWith(key)
                .compact();
    }

    public String refreshToken(String userAuthId, String refreshToken) {
        validateToken(refreshToken);

        return generateToken(userAuthId, ApiTokenType.ACCESS_TOKEN);
    }

    public void validateToken(String token) {
        if(isTokenExpired(token))
            throw new IllegalStateException("유효하지 않은 토큰입니다.");
    }

    private Boolean isTokenExpired(String token) {
        Date tokenExpirationDate = getClaimFromToken(token, Claims::getExpiration);
        Date now = new Date();
        return !tokenExpirationDate.after(now);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claimResolver.apply(claims);
    }
}
