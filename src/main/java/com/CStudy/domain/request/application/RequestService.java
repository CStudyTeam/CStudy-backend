package com.CStudy.domain.request.application;

import com.CStudy.domain.request.dto.request.CreateRequestRequestDto;
import com.CStudy.domain.request.dto.response.RequestResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RequestService {

    void createRequest(CreateRequestRequestDto requestDto, Long memberId);

    RequestResponseDto getRequest(Long id);

    Page<RequestResponseDto> getRequestList(Pageable pageable);

    Page<RequestResponseDto> getRequestList(Long memberId, Pageable pageable);


}
