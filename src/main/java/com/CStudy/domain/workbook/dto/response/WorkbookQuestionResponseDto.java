package com.CStudy.domain.workbook.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkbookQuestionResponseDto {

    private Long workbookQuestionId;

    private Long questionId;

    private String title;
}
