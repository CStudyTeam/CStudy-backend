package com.CStudy.domain.question.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class QuestionPageWithCategoryAndTitle {
    private Long questionId;
    private String questionTitle;
    private String categoryTitle;
    private int questionSuccess;

    @QueryProjection
    public QuestionPageWithCategoryAndTitle(Long questionId, String questionTitle, String categoryTitle, int questionSuccess) {
        this.questionId = questionId;
        this.questionTitle = questionTitle;
        this.categoryTitle = categoryTitle;
        this.questionSuccess = questionSuccess;
    }
}
