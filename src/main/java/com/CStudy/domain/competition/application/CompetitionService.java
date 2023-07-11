package com.CStudy.domain.competition.application;

import com.CStudy.domain.competition.dto.request.CompetitionQuestionRequestDto;
import com.CStudy.domain.competition.dto.request.CreateCompetitionRequestDto;
import com.CStudy.domain.competition.dto.response.CompetitionListResponseDto;
import com.CStudy.domain.competition.dto.response.CompetitionQuestionDto;
import com.CStudy.domain.competition.dto.response.CompetitionRankingResponseDto;
import com.CStudy.domain.competition.dto.response.CompetitionResponseDto;
import com.CStudy.global.util.LoginUserDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompetitionService {
    Long createCompetition(CreateCompetitionRequestDto createCompetitionRequestDto);

    CompetitionResponseDto getCompetition(Long id);

    Page<CompetitionListResponseDto> getCompetitionList(boolean finish, Pageable pageable);

    Page<CompetitionRankingResponseDto> getCompetitionRanking(Long id, Pageable pageable);

    List<CompetitionQuestionDto> getCompetitionQuestion(Long competitionId, LoginUserDto loginUserDto);

    void addCompetitionQuestion(CompetitionQuestionRequestDto requestDto);
    void deleteCompetitionQuestion(CompetitionQuestionRequestDto requestDto);
}
