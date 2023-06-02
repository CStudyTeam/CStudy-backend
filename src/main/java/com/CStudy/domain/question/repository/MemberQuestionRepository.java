package com.CStudy.domain.question.repository;

import com.CStudy.domain.question.entity.MemberQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberQuestionRepository extends JpaRepository<MemberQuestion, Long> {
}
