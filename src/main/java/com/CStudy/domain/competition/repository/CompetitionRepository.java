package com.CStudy.domain.competition.repository;

import com.CStudy.domain.competition.entity.Competition;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;

public interface CompetitionRepository extends JpaRepository<Competition, Long> {

    Page<Competition> findByCompetitionEndBefore(LocalDateTime time, Pageable pageable);

    Page<Competition> findByCompetitionEndAfter(LocalDateTime time, Pageable pageable);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select m from Competition m where m.id = :id")
    Optional<Competition> findByIdForUpdateOptimistic(@Param("id") Long id);

}
