package com.CStudy.domain.competition.controller;

import com.CStudy.domain.competition.application.CompetitionService;
import com.CStudy.domain.competition.application.MemberCompetitionService;
import com.CStudy.domain.competition.dto.request.createCompetitionRequestDto;
import com.CStudy.global.util.IfLogin;
import com.CStudy.global.util.LoginUserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
@Tag(name = "Competition(경기 API)", description = "경기 생성 및 Get")
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

    @Operation(summary = "대회 생성하기", description = "대회 생성하기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "대회 생성하기 성공")
    })
    @PostMapping("competition")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCompetition(
            @Parameter(name = "createCompetitionRequestDto", description = "createCompetitionRequestDto")
            @RequestBody createCompetitionRequestDto createCompetitionRequestDto
    ) {
        competitionService.createCompetition(createCompetitionRequestDto);
    }

    @PostMapping("join/competition/{competitionId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void joinCompetitionById(
            @IfLogin LoginUserDto loginUserDto,
            @PathVariable(name = "competitionId") Long competitionId
    ) {
        memberCompetitionService.joinCompetition(loginUserDto, competitionId);
    }
}
