package com.CStudy.domain.choice.repository;

import com.CStudy.domain.choice.entity.Choice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChoiceRepository extends JpaRepository<Choice, Long> {
}
