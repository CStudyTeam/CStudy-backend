package com.CStudy.domain.question.dto.response;

import com.CStudy.domain.choice.dto.ChoiceQuestionResponseDto;
import com.CStudy.domain.question.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponseDto {
    private String categoryTitle;
    private String title;
    private String description;
    private String explain;
    List<ChoiceQuestionResponseDto> choices = new ArrayList<>();

    public static QuestionResponseDto of(Question question) {
        return QuestionResponseDto.builder()
                .title(question.getTitle())
                .description(question.getDescription())
                .categoryTitle(question.getCategory().getCategoryTitle())
                .explain(question.getExplain())
                .choices(question.getChoices().stream()
                        .map(ChoiceQuestionResponseDto::new)
                        .collect(Collectors.toList()))
                .build();
    }
}
