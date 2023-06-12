package com.CStudy.domain.competition.application;

import com.CStudy.domain.competition.dto.request.createCompetitionRequestDto;
import com.CStudy.domain.competition.dto.response.CompetitionResponseDto;
import java.util.List;

public interface CompetitionService {
    void createCompetition(createCompetitionRequestDto createCompetitionRequestDto);

    CompetitionResponseDto getCompetition(Long id);
}
