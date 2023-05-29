package com.CStudy.utils.jwt;

import com.CStudy.utils.redis.RedisService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.time.Duration;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {

    private final long accessTokenValidTime = 30 * 60 * 1000L;
    private final long refreshTokenValidTime = 3 * 24 * 60 * 60 * 1000L;


    private final Key secretKey;
    private final RedisService redisService;

    public JwtTokenProvider(@Value("${jwt.key}") String key, RedisService redisService) {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.redisService = redisService;
    }

    public String createToken(Long userId){
        Date now = new Date();
        return Jwts.builder()
                .claim("userId", userId)
                .setExpiration(new Date(now.getTime() + accessTokenValidTime))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String createAccessToken(Long userId){
        return createToken(userId);
    }

    public String createRefreshToken(Long userId){
        String refreshToken = createToken(userId);
        redisService.setValues("refresh:" + String.valueOf(userId), refreshToken, Duration.ofMillis(refreshTokenValidTime));

        return refreshToken;
    }

    public String getAccessToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        return token.substring(7);
    }


    public Claims parseJwt(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public Long getUserId(String token){
        return parseJwt(token).get("userId", Long.class);
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }
}