package com.CStudy.domain.request.application.impl;

import com.CStudy.domain.aop.AuthCheckAnnotation;
import com.CStudy.domain.member.entity.Member;
import com.CStudy.domain.member.repository.MemberRepository;
import com.CStudy.domain.request.application.RequestService;
import com.CStudy.domain.request.dto.request.CreateRequestRequestDto;
import com.CStudy.domain.request.dto.request.FlagRequestDto;
import com.CStudy.domain.request.dto.request.UpdateRequestRequestDto;
import com.CStudy.domain.request.dto.response.RequestResponseDto;
import com.CStudy.domain.request.entity.Request;
import com.CStudy.domain.request.repository.RequestRepository;
import com.CStudy.global.exception.member.NotFoundMemberId;
import com.CStudy.global.exception.request.NotFoundRequest;
import com.CStudy.global.util.LoginUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final MemberRepository memberRepository;

    public RequestServiceImpl(RequestRepository requestRepository,
                              MemberRepository memberRepository) {
        this.requestRepository = requestRepository;
        this.memberRepository = memberRepository;
    }

    /**
     * Create requests for request problem.
     *
     * @param requestDto the request DTO containing the request information
     */
    @Override
    @Transactional
    public Long createRequest(
            CreateRequestRequestDto requestDto,
            Long memberId
    ) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberId(memberId));

        Request request = Request.builder()
                .title(requestDto.getTitle())
                .description(requestDto.getDescription())
                .member(member)
                .build();

        requestRepository.save(request);

        member.addRequest(request);

        return request.getId();
    }


    /**
     * Get requests for request problem.
     *
     * @param id id of request entity.
     */
    @Override
    @Transactional(readOnly = true)
    public RequestResponseDto getRequest(Long id) {

        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new NotFoundRequest(id));

        return RequestResponseDto.of(request);
    }

    /**
     * Get request list.
     *
     * @param pageable page information
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RequestResponseDto> getRequestList(Pageable pageable) {

        Page<Request> requests = requestRepository.findAll(pageable);

        return requests.map(RequestResponseDto::of);
    }

    /**
     * Get request list of member.
     *
     * @param memberId id of member who requested problem.
     * @param pageable page information
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RequestResponseDto> getRequestList(Long memberId, Pageable pageable) {

        Page<Request> request = requestRepository.findRequestByMemberId(memberId, pageable);

        return request.map(RequestResponseDto::of);
    }

    /**
     * Update flag for request problem. Only ADMIN can change flag
     *
     * @param flagDto id of request entity
     */
    @Override
    @Transactional
    public void updateFlag(FlagRequestDto flagDto) {
        Optional.of(flagDto)
                .filter(dto -> dto.getId() != 1)
                .ifPresent(dto -> {
                    throw new RuntimeException("권한이 일치하지 않습니다.");
                });
        Request request = requestRepository.findById(flagDto.getId())
                .orElseThrow(() -> new NotFoundRequest(flagDto.getId()));
        request.updateFlag(flagDto.isFlag());
    }

    @Override
    @AuthCheckAnnotation
    @Transactional
    public void deleteTodoById(Long id, LoginUserDto loginUserDto) {
        requestRepository.deleteById(id);
    }

    @Override
    @AuthCheckAnnotation
    @Transactional
    public void updateRequest(UpdateRequestRequestDto updateRequestRequestDto, LoginUserDto loginUserDto) {
        requestRepository.findById(updateRequestRequestDto.getId())
                .orElseThrow(() -> new NotFoundRequest(updateRequestRequestDto.getId()))
                .updateRequest(updateRequestRequestDto);
    }


}
