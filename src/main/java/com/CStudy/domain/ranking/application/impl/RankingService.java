package com.CStudy.domain.ranking.application.impl;

import com.CStudy.domain.member.entity.Member;
import com.CStudy.domain.member.repository.MemberRepository;
import com.CStudy.global.redis.RedisCacheKey;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RankingService implements com.CStudy.domain.ranking.application.RankingService {

    private final RedisTemplate<String, String> redisTemplate;
    private final MemberRepository memberRepository;

    public RankingService(RedisTemplate<String, String> redisTemplate, MemberRepository memberRepository) {
        this.redisTemplate = redisTemplate;
        this.memberRepository = memberRepository;
    }

    /**
     *
     * @return redis에 회원의 정보를 가져와 포인트를 0~10까지 가져온다.
     */
    @Cacheable(key = "1", value = RedisCacheKey.Ranking, cacheManager = "redisCacheManager")
    @Transactional(readOnly = true)
    public List<ZSetOperations.TypedTuple<String>> getRanking() {
        List<Member> memberList = memberRepository.findAll();

        ZSetOperations<String, String> stringStringZSetOperations = redisTemplate.opsForZSet();

        memberList.forEach(member -> stringStringZSetOperations.add("ranking", member.getName(), member.getRankingPoint()));

        return new ArrayList<>(Objects.requireNonNull(stringStringZSetOperations.rangeWithScores("ranking", 0, 10),"Ranking Board Data null"));
    }
}