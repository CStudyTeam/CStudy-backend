package com.CStudy.domain.competition.application.impl;

import com.CStudy.domain.competition.application.CompetitionService;
import com.CStudy.domain.competition.dto.request.createCompetitionRequestDto;
import com.CStudy.domain.competition.dto.response.CompetitionQuestionDto;
import com.CStudy.domain.competition.dto.response.CompetitionResponseDto;
import com.CStudy.domain.competition.entity.Competition;
import com.CStudy.domain.competition.repository.CompetitionRepository;
import com.CStudy.domain.question.repository.QuestionRepository;
import com.CStudy.domain.workbook.entity.Workbook;
import com.CStudy.domain.workbook.repository.WorkbookRepository;
import com.CStudy.global.exception.competition.NotFoundCompetitionId;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CompetitionServiceImpl implements CompetitionService {

    private final CompetitionRepository competitionRepository;
    private final WorkbookRepository workbookRepository;
    private final QuestionRepository questionRepository;


    public CompetitionServiceImpl(CompetitionRepository competitionRepository,
        WorkbookRepository workbookRepository,
        QuestionRepository questionRepository) {
        this.competitionRepository = competitionRepository;
        this.workbookRepository = workbookRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    @Transactional
    public void createCompetition(createCompetitionRequestDto createCompetitionRequestDto) {

        Workbook workbook = Workbook.builder()
                .title(createCompetitionRequestDto.getCompetitionTitle())
                .build();

        workbookRepository.save(workbook);

        Competition competition = Competition.builder()
                .competitionTitle(createCompetitionRequestDto.getCompetitionTitle())
                .participants(createCompetitionRequestDto.getParticipants())
                .competitionStart(LocalDateTime.now())
                .competitionEnd(createCompetitionRequestDto.getCompetitionEnd())
                .workbook(workbook)
                .build();

        competitionRepository.save(competition);
        workbook.setCompetition(competition);
    }

    @Override
    @Transactional
    public CompetitionResponseDto getCompetition(Long id) {

        Competition competition = competitionRepository.findById(id)
                .orElseThrow(() -> new NotFoundCompetitionId(id));
        List<CompetitionQuestionDto> question = questionRepository
                .findQuestionWithCompetitionById(id);

        return CompetitionResponseDto.of(competition, question);
    }

}
