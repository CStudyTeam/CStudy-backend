package com.CStudy.domain.competition.dto.response;

import com.CStudy.domain.choice.dto.ChoiceQuestionResponseDto;
import com.CStudy.domain.question.entity.Question;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompetitionQuestionDto {
    private Long questionId;
    private String description;
    private List<ChoiceQuestionResponseDto> choices;

    public static CompetitionQuestionDto of(Question question){
        return CompetitionQuestionDto.builder()
                .questionId(question.getId())
                .description(question.getDescription())
                .choices(question.getChoices().stream()
                    .map(ChoiceQuestionResponseDto::new)
                    .collect(Collectors.toList()))
                .build();
    }
}
