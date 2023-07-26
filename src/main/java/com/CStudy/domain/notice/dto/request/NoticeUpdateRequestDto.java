package com.CStudy.domain.notice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeUpdateRequestDto {
    private String title;
    private String content;
}
