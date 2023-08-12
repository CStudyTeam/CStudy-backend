package com.CStudy.domain.notice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoticeSearchRequestDto {

    private String title;
    private String content;
    private LocalDateTime createdDate;

}
