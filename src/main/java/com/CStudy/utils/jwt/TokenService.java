package com.CStudy.utils.jwt;

import com.CStudy.exception.UnAuthorizedException;
import com.CStudy.utils.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtTokenProvider tokenProvider;
    private final RedisService redisService;

    @Transactional
    public TokenResponseDto createToken(Long userId){
        String accessToken = tokenProvider.createAccessToken(userId);
        String refreshToken = tokenProvider.createRefreshToken(userId);

        return new TokenResponseDto(accessToken, refreshToken);
    }

    @Transactional
    public TokenResponseDto reissueToken(String refreshToken){
        if(!tokenProvider.validateToken(refreshToken)){
            throw new UnAuthorizedException("리프레시 토큰이 유효하지 않습니다.");
        }

        Long userId = tokenProvider.getUserId(refreshToken);
        if(!redisService.getValues("refresh:" + String.valueOf(userId)).equals(refreshToken)){
            throw new UnAuthorizedException("리프레시 토큰이 유효하지 않습니다.");
        }
        return new TokenResponseDto(tokenProvider.createAccessToken(userId), refreshToken);
    }
}
