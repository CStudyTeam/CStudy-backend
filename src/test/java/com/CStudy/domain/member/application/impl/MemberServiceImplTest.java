package com.CStudy.domain.member.application.impl;

import com.CStudy.domain.member.application.MemberService;
import com.CStudy.domain.member.dto.request.MemberLoginRequest;
import com.CStudy.domain.member.dto.request.MemberSignupRequest;
import com.CStudy.domain.member.dto.response.MemberLoginResponse;
import com.CStudy.domain.member.dto.response.MemberSignupResponse;
import com.CStudy.domain.member.entity.Member;
import com.CStudy.domain.member.repository.MemberRepository;
import com.CStudy.domain.role.entity.Role;
import com.CStudy.domain.role.repositry.RoleRepository;
import com.CStudy.global.exception.member.EmailDuplication;
import com.CStudy.global.exception.member.InvalidMatchPasswordException;
import com.CStudy.global.exception.member.NotFoundMemberEmail;
import com.CStudy.global.exception.member.NotFoundMemberId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("local")
class MemberServiceImplTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    private static final String VALID_EMAIL = "test1234@email.com";
    private static final String VALID_PASSWORD = "password1234!";

    @BeforeEach
    void setUp() {

    }

    @DisplayName("회원가입")
    @Nested
    class signup {
        @Test
        @DisplayName("회원가입 - 성공")
        public void ValidSignupWithCollectParameter() throws Exception {
            //given
            MemberSignupRequest memberSignupRequest = MemberSignupRequest.builder()
                    .email(VALID_EMAIL)
                    .password(VALID_PASSWORD)
                    .name("김무건")
                    .build();
            //when
            MemberSignupResponse memberSignupResponse = memberService.signUp(memberSignupRequest);
            //Then
            assertThat(memberSignupResponse.getEmail()).isEqualTo(VALID_EMAIL);
            assertThat(memberSignupResponse.getName()).isEqualTo("김무건");
        }

        @Test
        @DisplayName("회원가입 - 비밀번호 암호화 ")
        public void InvalidSignupWithNotEnoughParameter() throws Exception {
            //given
            MemberSignupRequest memberSignupRequest = MemberSignupRequest.builder()
                    .email(VALID_EMAIL)
                    .password(passwordEncoder.encode(VALID_PASSWORD))
                    .name("김무건")
                    .build();
            //when
            MemberSignupResponse memberSignupResponse = memberService.signUp(memberSignupRequest);

            Member member = memberRepository.findByEmail(VALID_EMAIL).orElseThrow(
                    () -> new NotFoundMemberEmail(memberSignupResponse.getEmail())
            );
            //Then
            assertThat(memberSignupResponse.getEmail()).isEqualTo(VALID_EMAIL);
            assertThat(member.getPassword()).isNotEqualTo(VALID_PASSWORD);
        }

        @Test
        @DisplayName("회원가입 - 이메일 중복")
        public void duplicationEmailWithInValidParameter() throws Exception {
            //given
            Member member = Member.builder()
                    .email(VALID_EMAIL)
                    .password(VALID_PASSWORD)
                    .build();

            memberRepository.save(member);

            MemberSignupRequest memberSignupRequest = MemberSignupRequest.builder()
                    .email(VALID_EMAIL)
                    .password(VALID_PASSWORD)
                    .name("김무건")
                    .build();

            //when Then
            assertThatThrownBy(() -> memberService.signUp(memberSignupRequest))
                    .isInstanceOf(EmailDuplication.class)
                    .hasMessage("User email is already existed:test1234@email.com");
        }

        @Test
        @DisplayName("회원가입 - 권한")
        public void signupValidRoles() throws Exception {
            //given
            MemberSignupRequest memberSignupRequest = MemberSignupRequest.builder()
                    .email(VALID_EMAIL)
                    .password(VALID_PASSWORD)
                    .name("김무건")
                    .build();

            //when
            memberService.signUp(memberSignupRequest);
            Member member = memberRepository.findByEmailWithRoles(VALID_EMAIL).orElseThrow();

            //Then
            assertThat(member.getRoles()).isNotNull();
        }
    }

    @DisplayName("권한 & init admin")
    @Nested
    class roles {

        @Test
        @DisplayName("회원 권한 생성")
        public void findRolesWithInitRoles() throws Exception {
            //given when
            List<Role> roles = roleRepository.findAll();

            //Then
            assertThat(roles.get(0).getName()).isEqualTo("ROLE_CUSTOM");
            assertThat(roles.get(1).getName()).isEqualTo("ROLE_ADMIN");
        }

        @Test
        @DisplayName("관리자 Init")
        public void validAdminInitWithParameter() throws Exception{
            //given when
            Member member = memberRepository.findById(1L)
                    .orElseThrow(() -> new NotFoundMemberId(1L));
            //Then
            assertThat(member.getName()).isEqualTo("관리자");
            assertThat(member.getEmail()).isEqualTo("admin");
            assertThat(member.getPassword()).isNotEqualTo("1234");

        }

    }

    @DisplayName("로그인")
    @Nested
    class login{

        @BeforeEach
        void setUp(){
            MemberSignupRequest memberSignupRequest = MemberSignupRequest.builder()
                    .email(VALID_EMAIL)
                    .password(VALID_PASSWORD)
                    .name("김무건")
                    .build();

            memberService.signUp(memberSignupRequest);
        }

        @Test
        @DisplayName("로그인 성공")
        public void validLoginWithParameter() throws Exception{
            //given
            MemberLoginRequest request = MemberLoginRequest.builder()
                    .email(VALID_EMAIL)
                    .password(VALID_PASSWORD)
                    .build();
            //when
            MemberLoginResponse login = memberService.login(request);
            //Then
            assertThat(login.getName()).isEqualTo("김무건");
            assertThat(login.getAccessToken()).isNotNull();
            assertThat(login.getEmail()).isEqualTo(VALID_EMAIL);
            assertThat(login.getRefreshToken()).isNotNull();
        }

        @Test
        @DisplayName("로그인 - 실패 (비밀번호 불일치)")
        public void InvalidLoginWithInvalidParameter() throws Exception{
            //given

            String InvalidPassword = "1234";

            MemberLoginRequest request = MemberLoginRequest.builder()
                    .email(VALID_EMAIL)
                    .password(InvalidPassword)
                    .build();
            //when
            //Then
            assertThatThrownBy(() -> memberService.login(request))
                    .isInstanceOf(InvalidMatchPasswordException.class)
                    .hasMessage(InvalidPassword+"가 일치하지 않습니다.");
        }

    }
}