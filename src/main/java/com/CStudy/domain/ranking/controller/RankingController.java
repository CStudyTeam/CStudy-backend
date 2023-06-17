package com.CStudy.domain.ranking.controller;

import com.CStudy.domain.ranking.application.impl.RankingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Ranking(전체 랭킹 API)", description = "전체 랭킹 조회")

@RestController
@RequestMapping("api")
public class RankingController {

    private final RankingService rankingService;

    public RankingController(
            RankingService rankingService
    ) {
        this.rankingService = rankingService;
    }

    @Operation(summary = "전체 랭킹 조회", description = "전체 랭킹 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "전체 랭킹 조회 성공")
    })
    @GetMapping("members/ranks")
    @ResponseStatus(HttpStatus.OK)
    public List<ZSetOperations.TypedTuple<String>> findMemberAllAboutRankingBoard() {
        return rankingService.getRanking();
    }
}
