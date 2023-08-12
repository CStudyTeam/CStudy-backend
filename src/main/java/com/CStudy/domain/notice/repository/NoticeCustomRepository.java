package com.CStudy.domain.notice.repository;

import com.CStudy.domain.notice.dto.request.NoticeSearchRequestDto;
import com.CStudy.domain.notice.dto.response.NoticeResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeCustomRepository {
    Page<NoticeResponseDto> findNoticePage(Pageable pageable, NoticeSearchRequestDto requestDto);
}
