package com.CStudy.domain.competition.dto.request;

import com.CStudy.domain.workbook.dto.request.QuestionIdRequestDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionQuestionRequestDto {

    private Long competitionId;
    private List<QuestionIdRequestDto> questionIds;

}
