package com.CStudy.domain.competition.dto.response;

import com.CStudy.domain.choice.dto.ChoiceQuestionResponseDto;
import com.CStudy.domain.competition.entity.Competition;
import com.CStudy.domain.question.dto.response.QuestionResponseDto;
import com.CStudy.domain.question.entity.Question;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompetitionResponseDto {

    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime;

    private List<CompetitionQuestionDto> questions;

    public static CompetitionResponseDto of(Competition competition, List<CompetitionQuestionDto> questions) {
        return CompetitionResponseDto.builder()
                .title(competition.getCompetitionTitle())
                .startTime(competition.getCompetitionStart())
                .endTime(competition.getCompetitionEnd())
                .questions(questions)
                .build();
    }

}
