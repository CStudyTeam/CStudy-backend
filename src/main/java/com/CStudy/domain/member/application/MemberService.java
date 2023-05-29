package com.CStudy.domain.member.application;


import com.CStudy.domain.member.dto.request.MemberLoginRequest;
import com.CStudy.domain.member.dto.request.MemberSignupRequest;
import com.CStudy.domain.member.dto.response.MemberLoginResponse;
import com.CStudy.domain.member.dto.response.MemberSignupResponse;

public interface MemberService {
    MemberSignupResponse signUp(MemberSignupRequest request);

    MemberLoginResponse login(MemberLoginRequest request);
}
