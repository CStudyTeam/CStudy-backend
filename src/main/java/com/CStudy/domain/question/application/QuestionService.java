package com.CStudy.domain.question.application;

import com.CStudy.domain.question.dto.CreateQuestionAndCategoryRequestDto;

public interface QuestionService {
    void createQuestionChoice(CreateQuestionAndCategoryRequestDto createChoicesAboutQuestionDto);
}
