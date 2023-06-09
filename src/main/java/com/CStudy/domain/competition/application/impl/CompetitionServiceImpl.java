package com.CStudy.domain.competition.application.impl;

import com.CStudy.domain.competition.application.CompetitionService;
import com.CStudy.domain.competition.dto.request.createCompetitionRequestDto;
import com.CStudy.domain.competition.entity.Competition;
import com.CStudy.domain.competition.repository.CompetitionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CompetitionServiceImpl implements CompetitionService {

    private final CompetitionRepository competitionRepository;


    public CompetitionServiceImpl(CompetitionRepository competitionRepository) {
        this.competitionRepository = competitionRepository;
    }

    @Override
    @Transactional
    public void createCompetition(createCompetitionRequestDto createCompetitionRequestDto) {


        Competition competition = Competition.builder()
                .competitionTitle(createCompetitionRequestDto.getCompetitionTitle())
                .participants(createCompetitionRequestDto.getParticipants())
                .competitionStart(LocalDateTime.now())
                .competitionEnd(createCompetitionRequestDto.getCompetitionEnd())
                .build();

        competitionRepository.save(competition);
    }
}
