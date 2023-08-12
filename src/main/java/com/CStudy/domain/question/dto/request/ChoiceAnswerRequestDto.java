package com.CStudy.domain.question.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChoiceAnswerRequestDto {
    private int choiceNumber;
    private Long time;
}
