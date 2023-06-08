package com.CStudy.domain.question.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class QuestionPageWithCategoryAndTitle {
    private Long questionId;
    private String questionTitle;
    private String categoryTitle;

    @QueryProjection

    public QuestionPageWithCategoryAndTitle(Long questionId, String questionTitle, String categoryTitle) {
        this.questionId = questionId;
        this.questionTitle = questionTitle;
        this.categoryTitle = categoryTitle;
    }
}
