package com.CStudy.domain.notice.application;

import com.CStudy.domain.notice.dto.request.NoticeSaveRequestDto;
import com.CStudy.domain.notice.dto.request.NoticeUpdateRequestDto;
import com.CStudy.global.util.LoginUserDto;

public interface NoticeService {
    void saveNotice(NoticeSaveRequestDto noticeSaveRequestDto, LoginUserDto loginUserDto);
    void updateNotice(Long noticeId, NoticeUpdateRequestDto noticeUpdateRequestDto, LoginUserDto loginUserDto);
    void deleteNotice(Long noticeId, LoginUserDto loginUserDto);
}
