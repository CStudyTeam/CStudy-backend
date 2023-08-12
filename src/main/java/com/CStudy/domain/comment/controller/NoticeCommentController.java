package com.CStudy.domain.comment.controller;

import com.CStudy.domain.comment.application.CommentService;
import com.CStudy.domain.comment.dto.request.NoticeCommentRequestDto;
import com.CStudy.global.util.IfLogin;
import com.CStudy.global.util.LoginUserDto;
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
