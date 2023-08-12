package com.CStudy.domain.choice.repository;

import com.CStudy.domain.choice.entity.Choice;
import com.CStudy.domain.question.entity.Question;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChoiceRepository extends JpaRepository<Choice, Long> {

    Optional<Choice> findByQuestionAndNumber(Question question, int number);
}
