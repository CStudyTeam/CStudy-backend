package com.CStudy.domain.choice.dto;

import com.CStudy.domain.choice.entity.Choice;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChoiceQuestionResponseDto {

    private int number;

    private String content;

    public ChoiceQuestionResponseDto(Choice choice) {
        this.number = choice.getNumber();
        this.content = choice.getContent();
    }
}
