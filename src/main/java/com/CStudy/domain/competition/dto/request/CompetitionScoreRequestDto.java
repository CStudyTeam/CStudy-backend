package com.CStudy.domain.competition.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class CompetitionScoreRequestDto {

    private Long competitionId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    private List<CompetitionQuestionRequestDto> questions;

    @Data
    public static class CompetitionQuestionRequestDto {
        private Long questionId;
        private int choiceNumber;
    }
}
