package com.CStudy.domain.notice.application;

import com.CStudy.domain.notice.dto.request.NoticeSaveRequestDto;
import com.CStudy.domain.notice.dto.request.NoticeSearchRequestDto;
import com.CStudy.domain.notice.dto.request.NoticeUpdateRequestDto;
import com.CStudy.domain.notice.dto.response.NoticeResponseDto;
import com.CStudy.global.util.LoginUserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeService {
    void saveNotice(NoticeSaveRequestDto noticeSaveRequestDto, LoginUserDto loginUserDto);
    void updateNotice(Long noticeId, NoticeUpdateRequestDto noticeUpdateRequestDto, LoginUserDto loginUserDto);
    void deleteNotice(Long noticeId, LoginUserDto loginUserDto);
    Page<NoticeResponseDto> findNoticePage(int page, int size, NoticeSearchRequestDto requestDto);
}
