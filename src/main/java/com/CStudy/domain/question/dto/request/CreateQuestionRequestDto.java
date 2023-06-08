package com.CStudy.domain.question.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateQuestionRequestDto {
    private String questionTitle;
    private String questionDesc;
    private String questionExplain;
}
