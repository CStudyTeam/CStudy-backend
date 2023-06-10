package com.CStudy.domain.workbook.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkbookQuestionRequestDto {

    private Long workbookId;
    private List<QuestionIdRequestDto> questionIds;

}
