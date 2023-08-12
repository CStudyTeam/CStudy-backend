package com.CStudy.domain.competition.application;

import com.CStudy.domain.competition.dto.request.CompetitionScoreRequestDto;
import com.CStudy.domain.competition.dto.response.CompetitionScoreResponseDto;
import com.CStudy.global.util.LoginUserDto;
import java.util.List;

public interface CompetitionScoreService {

    void scoring(CompetitionScoreRequestDto requestDto, LoginUserDto userDto);

    CompetitionScoreResponseDto getAnswer(Long memberId, Long competitionId);

    int getScore(Long memberId, Long competitionId);
}
