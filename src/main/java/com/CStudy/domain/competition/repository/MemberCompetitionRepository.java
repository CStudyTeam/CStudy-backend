package com.CStudy.domain.competition.repository;

import com.CStudy.domain.competition.entity.MemberCompetition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.List;

public interface MemberCompetitionRepository extends JpaRepository<MemberCompetition, Long> {

    @Query("SELECT MC FROM MemberCompetition MC " +
            "JOIN FETCH MC.member M " +
            "JOIN FETCH MC.competition C " +
            "WHERE C.id =: competitionId")
    List<MemberCompetition>findAllWithMemberAndCompetition(@Param("competitionId") Long competitionId);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT CASE WHEN COUNT(mc) > 0 THEN true ELSE false END " +
            "FROM MemberCompetition mc " +
            "WHERE mc.member.id = :memberId AND mc.competition.id = :competitionId")
    boolean existsByMemberIdAndCompetitionId(@Param("memberId") Long memberId, @Param("competitionId") Long competitionId);
}
