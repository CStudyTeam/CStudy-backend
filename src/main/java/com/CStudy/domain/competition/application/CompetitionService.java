package com.CStudy.domain.competition.application;

import com.CStudy.domain.competition.dto.request.createCompetitionRequestDto;
import com.CStudy.domain.competition.dto.response.CompetitionListResponseDto;
import com.CStudy.domain.competition.dto.response.CompetitionRankingResponseDto;
import com.CStudy.domain.competition.dto.response.CompetitionResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompetitionService {
    void createCompetition(createCompetitionRequestDto createCompetitionRequestDto);

    CompetitionResponseDto getCompetition(Long id);

    Page<CompetitionListResponseDto> getCompetitionList(boolean finish, Pageable pageable);

    Page<CompetitionRankingResponseDto> getCompetitionRanking(Long id, Pageable pageable);
}
