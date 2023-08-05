package com.cstudy.moduleapi.controller.notice;

import com.cstudy.moduleapi.application.notice.NoticeService;
import com.cstudy.modulecommon.dto.NoticeResponseDto;
import com.cstudy.moduleapi.dto.notice.NoticeSaveRequestDto;
import com.cstudy.modulecommon.dto.NoticeSearchRequestDto;
import com.cstudy.modulecommon.dto.NoticeUpdateRequestDto;
import com.cstudy.moduleapi.util.IfLogin;
import com.cstudy.modulecommon.util.LoginUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<NoticeResponseDto> findNoticeWithPage(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size,
            NoticeSearchRequestDto noticeSearchRequestDto
    ) {
        return noticeService.findNoticePage(size, page, noticeSearchRequestDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveNotice(@IfLogin LoginUserDto loginUserDto, @Valid @RequestBody NoticeSaveRequestDto noticeSaveRequestDto) {
        noticeService.saveNotice(noticeSaveRequestDto, loginUserDto);
    }

    @PutMapping("/{noticeId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateNotice(
            @PathVariable Long noticeId,
            @RequestBody NoticeUpdateRequestDto noticeUpdateRequestDto,
            @IfLogin LoginUserDto loginUserDto
    ) {
        noticeService.updateNotice(noticeId, noticeUpdateRequestDto, loginUserDto);
    }

    @DeleteMapping("/{noticeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNotice(@PathVariable Long noticeId, @IfLogin LoginUserDto loginUserDto) {
        noticeService.deleteNotice(noticeId, loginUserDto);
    }
}
