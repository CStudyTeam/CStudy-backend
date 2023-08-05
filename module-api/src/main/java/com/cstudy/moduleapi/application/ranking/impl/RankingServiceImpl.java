package com.cstudy.moduleapi.application.ranking.impl;

import com.cstudy.moduleapi.application.ranking.RankingService;
import com.cstudy.moduleapi.config.redis.RedisCacheKey;
import com.cstudy.modulecommon.domain.member.Member;
import com.cstudy.modulecommon.domain.question.MemberQuestion;
import com.cstudy.modulecommon.repository.member.MemberRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RankingServiceImpl implements RankingService {

    private final RedisTemplate<String, String> redisTemplate;
    private final MemberRepository memberRepository;

    public RankingServiceImpl(RedisTemplate<String, String> redisTemplate, MemberRepository memberRepository) {
        this.redisTemplate = redisTemplate;
        this.memberRepository = memberRepository;
    }

    /**
     * @return redis에 회원의 정보를 가져와 포인트를 0~10까지 가져온다.
     */
    @Cacheable(key = "1", value = RedisCacheKey.Ranking, cacheManager = "redisCacheManager")
    @Transactional(readOnly = true)
    public List<ZSetOperations.TypedTuple<String>> getRanking() {

        List<Member> memberList = memberRepository.findAllWithQuestions();

        Map<Long, Long> memberSolveTimeMap = memberList.stream()
                .collect(Collectors.toMap(Member::getId, this::calculateSolveTime));

        ZSetOperations<String, String> stringStringZSetOperations = redisTemplate.opsForZSet();

        memberList.forEach(member -> {
            double rankingPoint = member.getRankingPoint();
            stringStringZSetOperations.add("ranking", member.getName(), rankingPoint);
        });

        return new ArrayList<>(Objects.requireNonNull(stringStringZSetOperations.reverseRangeWithScores("ranking", 0, 9), "Ranking Board Data null"));
    }

    private long calculateSolveTime(Member member) {
        return member.getQuestions().stream()
                .mapToLong(MemberQuestion::getSolveTime)
                .sum();
    }
}
