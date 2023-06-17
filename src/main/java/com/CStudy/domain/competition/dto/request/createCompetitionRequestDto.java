package com.CStudy.domain.competition.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class createCompetitionRequestDto {
    private String competitionTitle;
    private int participants;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime competitionEnd;
}
