package com.CStudy.domain.request.application;

import com.CStudy.domain.request.dto.request.CreateRequestRequestDto;
import com.CStudy.domain.request.dto.request.FlagRequestDto;
import com.CStudy.domain.request.dto.request.UpdateRequestRequestDto;
import com.CStudy.domain.request.dto.response.RequestResponseDto;
import com.CStudy.global.util.LoginUserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RequestService {

    Long createRequest(CreateRequestRequestDto requestDto, Long memberId);

    RequestResponseDto getRequest(Long id);

    Page<RequestResponseDto> getRequestList(Pageable pageable);

    Page<RequestResponseDto> getRequestList(Long memberId, Pageable pageable);

    void updateFlag(FlagRequestDto flagDto);

    void deleteTodoById(Long id, LoginUserDto loginUserDto);

    void updateRequest(UpdateRequestRequestDto updateRequestRequestDto, LoginUserDto loginUserDto);

}
