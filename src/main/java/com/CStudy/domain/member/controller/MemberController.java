package com.CStudy.domain.member.controller;


import com.CStudy.domain.member.application.MemberService;
import com.CStudy.domain.member.dto.request.MemberLoginRequest;
import com.CStudy.domain.member.dto.request.MemberSignupRequest;
import com.CStudy.domain.member.dto.response.MemberLoginResponse;
import com.CStudy.domain.refresh.application.RefreshTokenService;
import com.CStudy.domain.refresh.dto.request.RefreshTokenDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;



@Slf4j
@RestController
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;
    private final RefreshTokenService refreshTokenService;

    public MemberController(
            MemberService memberService,
            RefreshTokenService refreshTokenService
    ) {
        this.memberService = memberService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUpWithRole(
            @Valid
            @RequestBody MemberSignupRequest request
    ) {
        log.info(String.format("request:>>{%s}", request));
        memberService.signUp(request);
    }

    @PostMapping("login")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberLoginResponse login(
            @Valid
            @RequestBody MemberLoginRequest request
    ) {
        log.info(String.format("request:>>{%s}", request));
        return memberService.login(request);
    }

    @DeleteMapping("logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(
            @RequestBody RefreshTokenDto refreshTokenDto
    ) {
        String refreshToken = refreshTokenDto.getRefreshToken();
        log.info(String.format("Refresh Token:>>{%s}", refreshToken));
        refreshTokenService.deleteRefreshToken(refreshToken);
    }

    @PostMapping("refreshToken")
    @ResponseStatus(HttpStatus.OK)
    public MemberLoginResponse refreshTokenWithAccessToken(
            @RequestBody RefreshTokenDto refreshTokenDto
    ) {
        log.info(String.format("Refresh Token:>>{%s}", refreshTokenDto));
        return refreshTokenService.AccessTokenWithRefreshToken(refreshTokenDto);
    }
}
