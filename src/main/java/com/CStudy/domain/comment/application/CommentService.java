package com.CStudy.domain.comment.application;

import com.CStudy.domain.comment.dto.request.NoticeCommentRequestDto;
import com.CStudy.global.util.LoginUserDto;

public interface CommentService {
    void saveNoticeComment(NoticeCommentRequestDto noticeCommentRequestDto, LoginUserDto loginUserDto);
}
