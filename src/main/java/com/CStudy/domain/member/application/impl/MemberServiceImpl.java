package com.CStudy.domain.member.application.impl;

import com.CStudy.domain.member.application.MemberService;
import com.CStudy.domain.member.dto.request.MemberLoginRequest;
import com.CStudy.domain.member.dto.request.MemberSignupRequest;
import com.CStudy.domain.member.dto.response.MemberLoginResponse;
import com.CStudy.domain.member.dto.response.MemberSignupResponse;
import com.CStudy.domain.member.entity.Member;
import com.CStudy.domain.member.repository.MemberRepository;
import com.CStudy.domain.refresh.application.RefreshTokenService;
import com.CStudy.domain.role.entity.Role;
import com.CStudy.domain.role.enums.RoleEnum;
import com.CStudy.domain.role.repositry.RoleRepository;
import com.CStudy.global.exception.member.EmailDuplication;
import com.CStudy.global.exception.member.NotFoundMemberEmail;
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

    private void signupWithRole(Member member) {
        Optional<Role> userRole = roleRepository.findByName(RoleEnum.CUSTOM.getRoleName());
        userRole.ifPresent(member::changeRole);
    }

    private void duplicationWithEmail(MemberSignupRequest request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new EmailDuplication(request.getEmail());
        }
    }
}
