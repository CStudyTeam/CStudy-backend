package com.CStudy.domain.competition.controller;

import com.CStudy.domain.competition.application.CompetitionScoreService;
import com.CStudy.domain.competition.application.CompetitionService;
import com.CStudy.domain.competition.application.MemberCompetitionService;
import com.CStudy.domain.competition.dto.request.CompetitionScoreRequestDto;
import com.CStudy.domain.competition.dto.request.CreateCompetitionRequestDto;
import com.CStudy.domain.competition.dto.response.CompetitionListResponseDto;
import com.CStudy.domain.competition.dto.response.CompetitionRankingResponseDto;
import com.CStudy.domain.competition.dto.response.CompetitionResponseDto;
import com.CStudy.global.exception.ErrorResponse;
import com.CStudy.global.util.IfLogin;
import com.CStudy.global.util.LoginUserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
            CompetitionScoreService competitionScoreService
    ) {
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
            @RequestBody CreateCompetitionRequestDto createCompetitionRequestDto
    ) {
        competitionService.createCompetition(createCompetitionRequestDto);
    }

    @Operation(summary = "대회 참여하기", description = "대회 id를 이용해 로그인 한 유저가 해당 대회 참여하기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "대회 참여하기 성공"),
            @ApiResponse(responseCode = "400", description = "대회 참여 인원 초과", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("join/competition/{competitionId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void joinCompetitionById(
            @Parameter(hidden = true)
            @IfLogin LoginUserDto loginUserDto,
            @Parameter(description = "대회 id")
            @PathVariable(name = "competitionId") Long competitionId
    ) {
        memberCompetitionService.joinCompetition(loginUserDto, competitionId);
    }

    @Operation(summary = "대회 정보", description = "대회 id를 이용해 대회 정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "대회 정보 조회 성공"),
            @ApiResponse(responseCode = "400", description = "대회 정보 조회 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("competition/{competitionId}")
    @ResponseStatus(HttpStatus.OK)
    public CompetitionResponseDto getCompetition(
            @Parameter(description = "대회 id")
            @PathVariable Long competitionId
    ) {
        return competitionService.getCompetition(competitionId);
    }

    @Operation(summary = "참여 가능 대회 리스트", description = "참여 가능 대회 리스트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "대회 리스트 조회 성공"),
            @ApiResponse(responseCode = "400", description = "대회 리스트 조회 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("competition/list")
    @ResponseStatus(HttpStatus.OK)
    public Page<CompetitionListResponseDto> getAvailableCompetition(
            @Parameter(description = "page: 페이지 번호, size: 한 페이지 문제 수.")
            @PageableDefault(sort = {"competitionStart"}, direction = Direction.ASC) Pageable pageable
    ) {
        return competitionService.getCompetitionList(false, pageable);
    }

    @Operation(summary = "종료된 대회 리스트", description = "종료된 대회 리스트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "대회 리스트 조회 성공"),
            @ApiResponse(responseCode = "400", description = "대회 리스트 조회 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("competition/finish/list")
    @ResponseStatus(HttpStatus.OK)
    public Page<CompetitionListResponseDto> getFinishCompetition(
            @Parameter(description = "page: 페이지 번호, size: 한 페이지 문제 수.")
            @PageableDefault(sort = {"competitionStart"}, direction = Direction.DESC) Pageable pageable
    ) {
        return competitionService.getCompetitionList(true, pageable);
    }

    @Operation(summary = "대회 랭킹", description = "대회 id를 이용해 대회에 참여한 회원들의 랭킹 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "랭킹 조회 성공"),
            @ApiResponse(responseCode = "400", description = "랭킹 조회 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("competition/ranking/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Page<CompetitionRankingResponseDto> getRanking(
            @Parameter(description = "page: 페이지 번호, size: 한 페이지 문제 수.")
            @PageableDefault @SortDefaults({
                @SortDefault(sort = "score", direction = Direction.DESC),
                @SortDefault(sort = "endTime",direction = Direction.ASC)
            }) Pageable pageable,
            @Parameter(description = "대회 id")
            @PathVariable Long id
    ) {
        return competitionService.getCompetitionRanking(id, pageable);
    }

    @Operation(summary = "대회 답안 제출", description = "문제를 다 풀고 답안 제출")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "제출 성공")
    })
    @PostMapping("competition/submit")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void submit(
            @Parameter(description = "competitionId: 대회 id, endTime: 제출한 시간, questions.questionId: 문제 id, questions.choiceNumber: 고른 선택지 번호")
            @RequestBody CompetitionScoreRequestDto requestDto,
            @Parameter(hidden = true)
            @IfLogin LoginUserDto loginUserDto
    ) {
        competitionScoreService.scoring(requestDto, loginUserDto);
    }
}
