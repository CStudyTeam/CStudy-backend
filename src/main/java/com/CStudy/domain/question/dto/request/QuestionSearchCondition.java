package com.CStudy.domain.question.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionSearchCondition {
    private String questionTitle;
    private String categoryTitle;
    private int questionSuccess;
    private Long memberId;
}
