package com.CStudy.domain.competition.controller;

import com.CStudy.domain.competition.application.CompetitionScoreService;
import com.CStudy.domain.competition.application.CompetitionService;
import com.CStudy.domain.competition.application.MemberCompetitionService;
import com.CStudy.domain.competition.dto.request.CompetitionScoreRequestDto;
import com.CStudy.domain.competition.dto.request.createCompetitionRequestDto;
import com.CStudy.domain.competition.dto.response.CompetitionListResponseDto;
import com.CStudy.domain.competition.dto.response.CompetitionRankingResponseDto;
import com.CStudy.domain.competition.dto.response.CompetitionResponseDto;
import com.CStudy.global.util.IfLogin;
import com.CStudy.global.util.LoginUserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.data.web.SortDefault.SortDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
@Tag(name = "Competition(경기 API)", description = "경기 생성 및 Get")
@Slf4j
@RestController
@RequestMapping("api")
public class CompetitionController {

    private final CompetitionService competitionService;
    private final MemberCompetitionService memberCompetitionService;
    private final CompetitionScoreService competitionScoreService;

    public CompetitionController(
            CompetitionService competitionService,
            MemberCompetitionService memberCompetitionService,
        CompetitionScoreService competitionScoreService) {
        this.competitionService = competitionService;
        this.memberCompetitionService = memberCompetitionService;
        this.competitionScoreService = competitionScoreService;
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

    @GetMapping("competition/ranking/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Page<CompetitionRankingResponseDto> getRanking(
            @PageableDefault @SortDefaults({
                @SortDefault(sort = "score", direction = Direction.DESC),
                @SortDefault(sort = "endTime",direction = Direction.ASC)
            }) Pageable pageable,
            @PathVariable Long id
    ) {
        return competitionService.getCompetitionRanking(id, pageable);
    }

    @PostMapping("competition/submit")
    @ResponseStatus(HttpStatus.CREATED)
    public void submit(
            @RequestBody CompetitionScoreRequestDto requestDto,
            @IfLogin LoginUserDto loginUserDto
    ) {
        competitionScoreService.scoring(requestDto, loginUserDto);
    }
}
