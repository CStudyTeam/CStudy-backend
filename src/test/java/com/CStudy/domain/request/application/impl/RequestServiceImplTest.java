package com.CStudy.domain.request.application.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.CStudy.domain.member.application.MemberService;
import com.CStudy.domain.member.dto.request.MemberLoginRequest;
import com.CStudy.domain.member.dto.request.MemberSignupRequest;
import com.CStudy.domain.member.dto.response.MemberLoginResponse;
import com.CStudy.domain.member.entity.Member;
import com.CStudy.domain.member.repository.MemberRepository;
import com.CStudy.domain.request.application.RequestService;
import com.CStudy.domain.request.dto.request.CreateRequestRequestDto;
import com.CStudy.domain.request.dto.request.FlagRequestDto;
import com.CStudy.domain.request.dto.response.RequestResponseDto;
import com.CStudy.global.exception.member.NotFoundMemberEmail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("local")
@Transactional
class RequestServiceImplTest {

    @Autowired
    private RequestService requestService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    private Long memberId1;
    private Long memberId2;

    @BeforeEach
    public void setUp(){
        MemberSignupRequest memberSignupRequest1 = MemberSignupRequest.builder()
                .email("test1@test.com")
                .name("테스트 유저1")
                .password("test1234!").build();
        memberService.signUp(memberSignupRequest1);

        MemberSignupRequest memberSignupRequest2 = MemberSignupRequest.builder()
                .email("test2@test.com")
                .name("테스트 유저2")
                .password("test1234!").build();
        memberService.signUp(memberSignupRequest2);

        Member member1 = memberRepository.findByEmail("test1@test.com")
                .orElseThrow(() -> new NotFoundMemberEmail("test1@test.com"));
        memberId1 = member1.getId();

        Member member2 = memberRepository.findByEmail("test2@test.com")
                .orElseThrow(() -> new NotFoundMemberEmail("test2@test.com"));
        memberId2 = member2.getId();
    }

    @Test
    @DisplayName("게시판 글 생성")
    public void createRequest(){
        CreateRequestRequestDto requestDto = CreateRequestRequestDto.builder()
                .title("문제 요청1")
                .description("문제 요청 내용1")
                .build();
        Long requestId = requestService.createRequest(requestDto, memberId1);
        RequestResponseDto request = requestService.getRequest(requestId);
        assertEquals(request.getTitle(), "문제 요청1");
        assertEquals(request.getDescription(), "문제 요청 내용1");
    }

    @Test
    @DisplayName("게시글 대기 -> 승인")
    public void approve(){
        CreateRequestRequestDto requestDto = CreateRequestRequestDto.builder()
                .title("문제 요청1")
                .description("문제 요청 내용1")
                .build();
        Long requestId = requestService.createRequest(requestDto, memberId1);

        RequestResponseDto request1 = requestService.getRequest(requestId);
        assertFalse(request1.isFlag());

        FlagRequestDto flag = new FlagRequestDto(requestId, true);
        requestService.updateFlag(flag);

        RequestResponseDto request2 = requestService.getRequest(requestId);
        assertTrue(request2.isFlag());
    }

    @Test
    @DisplayName("내 게시글 리스트 조회")
    public void myList(){
        for(int i = 1; i <= 10; i++) {
            CreateRequestRequestDto requestDto1 = CreateRequestRequestDto.builder()
                    .title("문제 요청" + i + " - member1")
                    .description("문제 요청 내용" + i)
                    .build();
            requestService.createRequest(requestDto1, memberId1);
            CreateRequestRequestDto requestDto2 = CreateRequestRequestDto.builder()
                    .title("문제 요청" + i + " - member2")
                    .description("문제 요청 내용" + i)
                    .build();
            requestService.createRequest(requestDto2, memberId2);
        }
        Pageable pageable = PageRequest.of(0, 5, Sort.by("createdAt").descending());
        Page<RequestResponseDto> requestList = requestService.getRequestList(memberId1, pageable);
        for (int i = 10; i >= 6; i--) {
            assertEquals(requestList.getContent().get(10-i).getTitle(), "문제 요청" + i + " - member1");
        }
    }

    @Test
    @DisplayName("게시글 리스트 조회")
    public void requestList() {
        for(int i = 1; i <= 10; i++) {
            CreateRequestRequestDto requestDto = CreateRequestRequestDto.builder()
                    .title("문제 요청"+i)
                    .description("문제 요청 내용"+i)
                    .build();
            Long requestId = requestService.createRequest(requestDto, memberId1);
        }
        Pageable pageable = PageRequest.of(0, 5, Sort.by("createdAt").descending());
        Page<RequestResponseDto> requestList = requestService.getRequestList(memberId1, pageable);
        for (int i = 10; i >= 6; i--) {
            assertEquals(requestList.getContent().get(10-i).getTitle(), "문제 요청"+i);
            assertEquals(requestList.getContent().get(10-i).getDescription(), "문제 요청 내용"+i);
        }
    }
}