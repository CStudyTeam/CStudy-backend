package com.CStudy.domain.member.application.impl;

import com.CStudy.domain.aop.TimeAnnotation;
import com.CStudy.domain.member.application.FileService;
import com.CStudy.domain.member.application.MemberService;
import com.CStudy.domain.member.dto.request.MemberLoginRequest;
import com.CStudy.domain.member.dto.request.MemberPasswordChangeRequest;
import com.CStudy.domain.member.dto.request.MemberSignupRequest;
import com.CStudy.domain.member.dto.response.MemberLoginResponse;
import com.CStudy.domain.member.dto.response.MemberSignupResponse;
import com.CStudy.domain.member.dto.response.MyPageResponseDto;
import com.CStudy.domain.member.entity.Member;
import com.CStudy.domain.member.repository.MemberRepository;
import com.CStudy.domain.refresh.application.RefreshTokenService;
import com.CStudy.domain.role.entity.Role;
import com.CStudy.domain.role.enums.RoleEnum;
import com.CStudy.domain.role.repositry.RoleRepository;
import com.CStudy.global.exception.member.EmailDuplication;
import com.CStudy.global.exception.member.InvalidMatchPasswordException;
import com.CStudy.global.exception.member.NotFoundMemberEmail;
import com.CStudy.global.exception.member.NotFoundMemberId;
import com.CStudy.global.jwt.util.JwtTokenizer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenizer jwtTokenizer;
    private final RefreshTokenService refreshTokenService;

    public MemberServiceImpl(
            MemberRepository memberRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenizer jwtTokenizer,
            RefreshTokenService refreshTokenService
    ) {
        this.memberRepository = memberRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenizer = jwtTokenizer;
        this.refreshTokenService = refreshTokenService;
    }

    /**
     * Returns the MemberSignupResponse With request
     *
     * @param request 회원가입 Signup
     * @return 회원가입 성공을 하면 이메일, 이름을 리턴을 합니다.
     * @throws EmailDuplication 중복된 이메일 회원가입 요청
     */
    @Override
    @Transactional
    @TimeAnnotation
    public MemberSignupResponse signUp(
            MemberSignupRequest request
    ) {
        duplicationWithEmail(request);

        Member member = Member.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .roles(new HashSet<>())
                .build();

        signupWithRole(member);

        return MemberSignupResponse.of(memberRepository.save(member));
    }

    /**
     * Returns login member with LoginRequest
     *
     * @param request 회원 로그인 Request Dto
     * @return 로그인 성공하면 회원 아이디, JWT(Access, Refresh Token)을 리턴을 합니다.
     */
    @Override
    @Transactional
    public MemberLoginResponse login(MemberLoginRequest request) {

        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundMemberEmail(request.getEmail()));

        if(!passwordEncoder.matches(request.getPassword(), member.getPassword())){
            throw new InvalidMatchPasswordException(request.getPassword());
        }

        return createToken(member);
    }

    /**
     * oauthSignUp
     *
     * @param email 회원 이름
     * @param name 회원 이름
     */
    @Override
    @Transactional
    public Member oauthSignUp(String email, String name) {

        Member member = Member.builder()
                .email(email)
                .name(name)
                .roles(new HashSet<>())
                .build();

        signupWithRole(member);

        memberRepository.save(member);

        return member;
    }

    /**
     * Returns login member with LoginRequest
     *
     * @param email 회원 이메일
     * @return 로그인 성공하면 회원 아이디, JWT(Access, Refresh Token)을 리턴을 합니다.
     */
    @Override
    @Transactional(readOnly = true)
    public MemberLoginResponse oauthLogin(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundMemberEmail(email));

        return createToken(member);
    }

    @Override
    @Transactional(readOnly = true)
    public MyPageResponseDto getMyPage(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new NotFoundMemberId(id));

        return MyPageResponseDto.of(member);
    }

    @Override
    @Transactional
    public void changePassword(MemberPasswordChangeRequest request, Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new NotFoundMemberId(id));

        if(!passwordEncoder.matches(request.getOldPassword(), member.getPassword())){
            throw new InvalidMatchPasswordException(request.getOldPassword());
        }

        String newPassword = passwordEncoder.encode(request.getNewPassword());
        member.changePassword(newPassword);
    }


    private void signupWithRole(Member member) {
        Optional<Role> userRole = roleRepository.findByName(RoleEnum.CUSTOM.getRoleName());
        userRole.ifPresent(member::changeRole);
    }

    private void duplicationWithEmail(MemberSignupRequest request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new EmailDuplication(request.getEmail());
        }
    }

    private MemberLoginResponse createToken(Member member){

        List<String> roles = member.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        String accessToken = jwtTokenizer.createAccessToken(
                member.getId(),
                member.getEmail(),
                roles
        );

        String refreshToken = jwtTokenizer.createRefreshToken(
                member.getId(),
                member.getEmail(),
                roles
        );

        refreshTokenService.addRefreshToken(refreshToken);
        return MemberLoginResponse.of(member, accessToken, refreshToken);
    }
}
