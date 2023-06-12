package com.CStudy.domain.competition.controller;

import com.CStudy.domain.competition.application.CompetitionService;
import com.CStudy.domain.competition.application.MemberCompetitionService;
import com.CStudy.domain.competition.dto.request.createCompetitionRequestDto;
import com.CStudy.domain.competition.dto.response.CompetitionListResponseDto;
import com.CStudy.domain.competition.dto.response.CompetitionQuestionDto;
import com.CStudy.domain.competition.dto.response.CompetitionResponseDto;
import com.CStudy.global.util.IfLogin;
import com.CStudy.global.util.LoginUserDto;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api")
public class CompetitionController {

    private final CompetitionService competitionService;
    private final MemberCompetitionService memberCompetitionService;

    public CompetitionController(
            CompetitionService competitionService,
            MemberCompetitionService memberCompetitionService
    ) {
        this.competitionService = competitionService;
        this.memberCompetitionService = memberCompetitionService;
    }

    @PostMapping("competition")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCompetition(
            @RequestBody createCompetitionRequestDto createCompetitionRequestDto
    ) {
        competitionService.createCompetition(createCompetitionRequestDto);
    }

    @PostMapping("join/competition/{competitionId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void joinCompetitionById(
            @IfLogin LoginUserDto loginUserDto,
            @PathVariable Long competitionId
    ) {
        memberCompetitionService.joinCompetition(loginUserDto, competitionId);
    }

    @GetMapping("competition/{competitionId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CompetitionResponseDto getCompetition(
            @PathVariable Long competitionId
    ) {
        return competitionService.getCompetition(competitionId);
    }

    @GetMapping("competition/list")
    @ResponseStatus(HttpStatus.CREATED)
    public Page<CompetitionListResponseDto> getAvailableCompetition(
            @PageableDefault(sort = {"competitionStart"}, direction = Direction.ASC) Pageable pageable
    ) {
        return competitionService.getCompetitionList(false, pageable);
    }

    @GetMapping("competition/finish/list")
    @ResponseStatus(HttpStatus.CREATED)
    public Page<CompetitionListResponseDto> getFinishCompetition(
            @PageableDefault(sort = {"competitionStart"}, direction = Direction.DESC) Pageable pageable
    ) {
        return competitionService.getCompetitionList(true, pageable);
    }
}
