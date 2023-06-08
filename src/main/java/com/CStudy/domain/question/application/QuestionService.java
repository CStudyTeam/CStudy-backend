package com.CStudy.domain.question.application;

import com.CStudy.domain.question.dto.request.ChoiceAnswerRequestDto;
import com.CStudy.domain.question.dto.request.CreateQuestionAndCategoryRequestDto;
import com.CStudy.domain.question.dto.request.QuestionSearchCondition;
import com.CStudy.domain.question.dto.response.QuestionPageWithCategoryAndTitle;
import com.CStudy.domain.question.dto.response.QuestionResponseDto;
import com.CStudy.global.util.LoginUserDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface QuestionService {
    void createQuestionChoice(CreateQuestionAndCategoryRequestDto createChoicesAboutQuestionDto);

    void recursiveCreateQuestionChoice(List<CreateQuestionAndCategoryRequestDto> requestDtos);

    QuestionResponseDto findQuestionWithChoiceAndCategory(Long questionId);

    void choiceQuestion(LoginUserDto loginUserDto, Long questionId, ChoiceAnswerRequestDto choiceNumber);

    Page<QuestionPageWithCategoryAndTitle>questionPageWithCategory(QuestionSearchCondition searchCondition, int page, int size);
}
