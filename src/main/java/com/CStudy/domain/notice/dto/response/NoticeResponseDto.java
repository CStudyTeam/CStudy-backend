package com.CStudy.domain.notice.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
public class NoticeResponseDto {
    private String title;
    private String content;
    private LocalDateTime createDate;

    @QueryProjection
    public NoticeResponseDto(String title, String content, LocalDateTime createDate) {
        this.title = title;
        this.content = content;
        this.createDate = createDate;
    }
}
