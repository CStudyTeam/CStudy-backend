package com.CStudy.domain.question.repository;

import com.CStudy.domain.question.entity.MemberQuestion;
import com.CStudy.domain.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
