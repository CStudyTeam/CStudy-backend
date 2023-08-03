package com.CStudy.domain.member.application;


import com.CStudy.domain.member.dto.request.MemberLoginRequest;
import com.CStudy.domain.member.dto.request.MemberPasswordChangeRequest;
import com.CStudy.domain.member.dto.request.MemberSignupRequest;
import com.CStudy.domain.member.dto.response.MemberLoginResponse;
import com.CStudy.domain.member.dto.response.MemberSignupResponse;
import com.CStudy.domain.member.dto.response.MyPageResponseDto;
import com.CStudy.domain.member.entity.Member;
import org.springframework.mail.MailException;

import javax.mail.MessagingException;

public interface MemberService {
    MemberSignupResponse signUp(MemberSignupRequest request);

    MemberLoginResponse login(MemberLoginRequest request);

    Member oauthSignUp(String email, String name);

    MemberLoginResponse oauthLogin(String email);

    MyPageResponseDto getMyPage(Long id);

    void changePassword(MemberPasswordChangeRequest request, Long id);

    String sendEmail(String recipientEmail) throws MailException, MessagingException;
}
