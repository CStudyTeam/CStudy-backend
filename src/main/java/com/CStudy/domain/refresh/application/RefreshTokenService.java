package com.CStudy.domain.refresh.application;


import com.CStudy.domain.member.dto.response.MemberLoginResponse;
import com.CStudy.domain.refresh.dto.request.RefreshTokenDto;

public interface RefreshTokenService {
    void addRefreshToken(String refreshToken);

    void deleteRefreshToken(String refreshToken);

    MemberLoginResponse AccessTokenWithRefreshToken(RefreshTokenDto refreshTokenDto);
}
