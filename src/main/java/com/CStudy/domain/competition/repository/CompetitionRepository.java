package com.CStudy.domain.competition.repository;

import com.CStudy.domain.competition.entity.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitionRepository extends JpaRepository<Competition, Long> {
}
