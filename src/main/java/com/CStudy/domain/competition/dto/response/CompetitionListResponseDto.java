package com.CStudy.domain.competition.dto.response;

import com.CStudy.domain.competition.entity.Competition;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionListResponseDto {

    private Long id;
    private String title;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime;
    private int participants;

    public static CompetitionListResponseDto of(Competition competition){
        return CompetitionListResponseDto.builder()
                .id(competition.getId())
                .title(competition.getCompetitionTitle())
                .startTime(competition.getCompetitionStart())
                .endTime(competition.getCompetitionEnd())
                .participants(competition.getParticipants())
                .build();
    }
}
