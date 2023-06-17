package com.CStudy.domain.ranking.application;

import org.springframework.data.redis.core.ZSetOperations;

import java.util.List;

public interface RankingService {
    List<ZSetOperations.TypedTuple<String>> getRanking();
}
