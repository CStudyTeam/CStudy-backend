package com.cstudy.moduleapi.application.refershToken.impl;

import com.cstudy.moduleapi.application.refershToken.RefreshTokenService;
import com.cstudy.moduleapi.config.jwt.util.JwtTokenizer;
import com.cstudy.moduleapi.config.redis.RedisService;
import com.cstudy.moduleapi.dto.member.MemberLoginResponse;
import com.cstudy.moduleapi.dto.refresh.RefreshTokenDto;
import com.cstudy.modulecommon.domain.member.Member;
import com.cstudy.modulecommon.error.member.NotFoundMemberId;
import com.cstudy.modulecommon.repository.member.MemberRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final JwtTokenizer jwtTokenizer;
    private final MemberRepository memberRepository;
    private final RedisService redisService;

    @Override
    @Transactional
    public void addRefreshToken(String refreshToken) {
        redisService.setValues(refreshToken);
    }

    @Override
    @Transactional
    public void deleteRefreshToken(String refreshToken) {
        redisService.delValues(refreshToken);
    }

    /**
     *
     * @param refreshTokenDto refresh Token의 정보
     * @return MemberLoginResponse 재할당 access Token, refresh Token, member의 정보를 보여준다.
     */
    @Override
    public MemberLoginResponse AccessTokenWithRefreshToken(RefreshTokenDto refreshTokenDto) {

        String refreshToken = redisService.getValues(refreshTokenDto.getRefreshToken());

        Claims claims = jwtTokenizer.parseRefreshToken(refreshToken);
        Long userId = Long.valueOf((Integer) claims.get("memberId"));

        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new NotFoundMemberId(userId));

        List roles = (List) claims.get("roles");
        String email = claims.getSubject();
        String accessToken = jwtTokenizer.createAccessToken(userId, email, roles);

        return MemberLoginResponse.of(member, accessToken, refreshTokenDto.getRefreshToken());
    }

}
