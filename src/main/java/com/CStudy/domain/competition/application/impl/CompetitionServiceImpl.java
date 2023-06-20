package com.CStudy.domain.competition.application.impl;

import com.CStudy.domain.competition.application.CompetitionService;
import com.CStudy.domain.competition.application.MemberCompetitionService;
import com.CStudy.domain.competition.dto.request.CreateCompetitionRequestDto;
import com.CStudy.domain.competition.dto.response.CompetitionListResponseDto;
import com.CStudy.domain.competition.dto.response.CompetitionQuestionDto;
import com.CStudy.domain.competition.dto.response.CompetitionRankingResponseDto;
import com.CStudy.domain.competition.dto.response.CompetitionResponseDto;
import com.CStudy.domain.competition.entity.Competition;
import com.CStudy.domain.competition.entity.MemberCompetition;
import com.CStudy.domain.competition.repository.CompetitionRepository;
import com.CStudy.domain.competition.repository.MemberCompetitionRepository;
import com.CStudy.domain.question.repository.QuestionRepository;
import com.CStudy.domain.workbook.entity.Workbook;
import com.CStudy.domain.workbook.repository.WorkbookRepository;
import com.CStudy.global.exception.competition.NotFoundCompetitionId;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CompetitionServiceImpl implements CompetitionService {

    private final CompetitionRepository competitionRepository;
    private final WorkbookRepository workbookRepository;
    private final QuestionRepository questionRepository;
    private final MemberCompetitionRepository memberCompetitionRepository;
    private final MemberCompetitionService memberCompetitionService;

    public CompetitionServiceImpl(
        CompetitionRepository competitionRepository,
        WorkbookRepository workbookRepository,
        QuestionRepository questionRepository,
        MemberCompetitionRepository memberCompetitionRepository,
        MemberCompetitionService memberCompetitionService
    ) {
        this.competitionRepository = competitionRepository;
        this.workbookRepository = workbookRepository;
        this.questionRepository = questionRepository;
        this.memberCompetitionRepository = memberCompetitionRepository;
        this.memberCompetitionService = memberCompetitionService;
    }

    @Override
    @Transactional
    public Long createCompetition(CreateCompetitionRequestDto createCompetitionRequestDto) {

        Workbook workbook = Workbook.builder()
                .title(createCompetitionRequestDto.getCompetitionTitle())
                .description(createCompetitionRequestDto.getCompetitionTitle())
                .build();

        workbookRepository.save(workbook);

        Competition competition = Competition.builder()
                .competitionTitle(createCompetitionRequestDto.getCompetitionTitle())
                .participants(createCompetitionRequestDto.getParticipants())
                .competitionStart(createCompetitionRequestDto.getCompetitionStart())
                .competitionEnd(createCompetitionRequestDto.getCompetitionEnd())
                .workbook(workbook)
                .build();

        competitionRepository.save(competition);
        workbook.setCompetition(competition);

        return competition.getId();
    }

    /**
     * 대회 정보.
     *
     * @param competitionId competition id
     */
    @Override
    @Transactional(readOnly = true)
    public CompetitionResponseDto getCompetition(Long competitionId) {

        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new NotFoundCompetitionId(competitionId));

        int participants = memberCompetitionService.getJoinMemberCount(competitionId);

        return CompetitionResponseDto.of(competition, participants);
    }

    /**
     * 대회 리스트.
     *
     * @param finish 끝난 대회이면 true, 진행 전 대회이면 false
     * @param pageable pageable
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CompetitionListResponseDto> getCompetitionList(boolean finish, Pageable pageable) {

        Page<Competition> competitions = null;
        if(finish) {
            competitions = competitionRepository.findByCompetitionEndBefore(LocalDateTime.now(), pageable);
        } else {
            competitions = competitionRepository.findByCompetitionEndAfter(LocalDateTime.now(), pageable);
        }

        return competitions.map(CompetitionListResponseDto::of);
    }

    /**
     * 대회 랭킹.
     *
     * @param competitionId competition id
     * @param pageable pageable
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CompetitionRankingResponseDto> getCompetitionRanking(Long competitionId, Pageable pageable) {
        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new NotFoundCompetitionId(competitionId));
        Page<MemberCompetition> memberRanking = memberCompetitionRepository.findByCompetition(competition, pageable);

        return memberRanking.map(CompetitionRankingResponseDto::of);
    }

    @Override
    public List<CompetitionQuestionDto> getCompetitionQuestion(Long competitionId) {
        return questionRepository.findQuestionWithCompetitionById(competitionId);
    }

}
