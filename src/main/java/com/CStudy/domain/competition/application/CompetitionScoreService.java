package com.CStudy.domain.competition.application;

import com.CStudy.domain.competition.dto.request.CompetitionScoreRequestDto;
import com.CStudy.global.util.LoginUserDto;

public interface CompetitionScoreService {

    void scoring(CompetitionScoreRequestDto requestDto, LoginUserDto userDto);

    int getScore(Long memberId, Long competitionId);
}
