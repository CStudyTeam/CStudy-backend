package com.CStudy.domain.competition.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class createCompetitionRequestDto {
    private String competitionTitle;
    private int participants;
    private LocalDateTime competitionEnd;
}
