package com.CStudy.domain.competition.dto.response;

import com.CStudy.domain.competition.entity.MemberCompetition;
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
public class CompetitionRankingResponseDto {

    private String name;
    private Long memberId;
    private int score;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    public static CompetitionRankingResponseDto of(MemberCompetition memberCompetition){
        return CompetitionRankingResponseDto.builder()
                .name(memberCompetition.getMember().getName())
                .memberId(memberCompetition.getMember().getId())
                .score(memberCompetition.getScore())
                .endTime(memberCompetition.getEndTime())
                .build();
    }
}
