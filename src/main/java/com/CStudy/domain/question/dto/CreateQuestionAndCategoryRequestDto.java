package com.CStudy.domain.question.dto;

import com.CStudy.domain.choice.dto.CreateChoicesAboutQuestionDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateQuestionAndCategoryRequestDto {
    private CreateQuestionRequestDto createQuestionRequestDto;
    private CategoryRequestDto categoryRequestDto;
    private List<CreateChoicesAboutQuestionDto> createChoicesAboutQuestionDto;
}
