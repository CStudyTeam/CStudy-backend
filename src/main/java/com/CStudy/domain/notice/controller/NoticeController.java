package com.CStudy.domain.notice.controller;

import com.CStudy.domain.notice.application.NoticeService;
import com.CStudy.domain.notice.dto.request.NoticeSaveRequestDto;
import com.CStudy.domain.notice.dto.request.NoticeUpdateRequestDto;
import com.CStudy.global.util.IfLogin;
import com.CStudy.global.util.LoginUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void saveNotice(@IfLogin LoginUserDto loginUserDto, @RequestBody NoticeSaveRequestDto noticeSaveRequestDto) {
        noticeService.saveNotice(noticeSaveRequestDto, loginUserDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateNotice(@RequestBody NoticeUpdateRequestDto noticeUpdateRequestDto, @IfLogin LoginUserDto loginUserDto) {
        noticeService.updateNotice(noticeUpdateRequestDto, loginUserDto);
    }

    @DeleteMapping("/{noticeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNotice(@PathVariable Long noticeId, @IfLogin LoginUserDto loginUserDto) {
        noticeService.deleteNotice(noticeId, loginUserDto);
    }
}
