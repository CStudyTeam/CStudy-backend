package com.CStudy.domain.ranking.controller;

import com.CStudy.domain.ranking.application.impl.RankingService;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class RankingController {

    private final RankingService rankingService;

    public RankingController(
            RankingService rankingService
    ) {
        this.rankingService = rankingService;
    }

    @GetMapping("members/ranks")
    public List<ZSetOperations.TypedTuple<String>> findMemberAllAboutRankingBoard() {
        return rankingService.getRanking();
    }
}
