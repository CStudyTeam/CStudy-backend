package com.CStudy.domain.competition.repository;

import com.CStudy.domain.competition.entity.Competition;
import com.CStudy.domain.competition.entity.MemberCompetition;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberCompetitionRepository extends JpaRepository<MemberCompetition, Long> {

    @Query("SELECT MC FROM MemberCompetition MC " +
            "JOIN FETCH MC.member M " +
            "JOIN FETCH MC.competition C " +
            "WHERE C.id =: competitionId")
    List<MemberCompetition>findAllWithMemberAndCompetition(@Param("competitionId") Long competitionId);

    @Query("SELECT CASE WHEN COUNT(mc) > 0 THEN true ELSE false END " +
            "FROM MemberCompetition mc " +
            "WHERE mc.member.id = :memberId AND mc.competition.id = :competitionId")
    boolean existsByMemberIdAndCompetitionId(@Param("memberId") Long memberId, @Param("competitionId") Long competitionId);

    @Query("SELECT MC FROM MemberCompetition MC " +
            "LEFT JOIN Competition C ON C = MC.competition " +
            "LEFT JOIN Member M ON M = MC.member " +
            "WHERE M.id = :memberId " +
            "AND C.id = :competitionId"
    )
    Optional<MemberCompetition> findByMemberIdAndCompetitionId(@Param("memberId") Long memberId,
                                                    @Param("competitionId") Long competitionId);
    @EntityGraph(attributePaths = {"member"})
    Page<MemberCompetition> findByCompetition(Competition competition, Pageable pageable);
}
