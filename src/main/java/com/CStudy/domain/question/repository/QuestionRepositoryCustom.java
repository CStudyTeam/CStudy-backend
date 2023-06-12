package com.CStudy.domain.question.repository;

import com.CStudy.domain.competition.dto.response.CompetitionQuestionDto;
import com.CStudy.domain.question.dto.request.QuestionSearchCondition;
import com.CStudy.domain.question.dto.response.QuestionPageWithCategoryAndTitle;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionRepositoryCustom {
    Page<QuestionPageWithCategoryAndTitle> findQuestionPageWithCategory(Pageable pageable, QuestionSearchCondition questionSearchCondition);

    List<CompetitionQuestionDto> findQuestionWithCompetitionById(Long id);
}
