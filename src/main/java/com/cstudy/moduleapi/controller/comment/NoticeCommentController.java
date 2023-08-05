package com.cstudy.moduleapi.controller.comment;

import com.cstudy.moduleapi.application.comment.CommentService;
import com.cstudy.moduleapi.dto.comment.NoticeCommentRequestDto;
import com.cstudy.moduleapi.util.IfLogin;
import com.cstudy.modulecommon.util.LoginUserDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoticeCommentController {

    private final CommentService commentService;

    public NoticeCommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/api/comment")
    @ResponseStatus(HttpStatus.OK)
    public void postNewNoticeComment(
            @RequestBody NoticeCommentRequestDto noticeCommentRequestDto,
            @IfLogin LoginUserDto loginUserDto
    ) {
        commentService.saveNoticeComment(noticeCommentRequestDto, loginUserDto);
    }
}
