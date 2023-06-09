package com.CStudy.domain.question.controller;

import com.CStudy.domain.question.application.QuestionService;
import com.CStudy.domain.question.dto.request.ChoiceAnswerRequestDto;
import com.CStudy.domain.question.dto.request.CreateQuestionAndCategoryRequestDto;
import com.CStudy.domain.question.dto.request.QuestionSearchCondition;
import com.CStudy.domain.question.dto.response.QuestionPageWithCategoryAndTitle;
import com.CStudy.domain.question.dto.response.QuestionResponseDto;
import com.CStudy.global.util.IfLogin;
import com.CStudy.global.util.LoginUserDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("question")
    @ResponseStatus(HttpStatus.CREATED)
    public void createQuestionWithCategory(
            @RequestBody CreateQuestionAndCategoryRequestDto requestDto
    ) {
        questionService.createQuestionChoice(requestDto);
    }

    @PostMapping("questions")
    @ResponseStatus(HttpStatus.CREATED)
    public void buildCreateQuestionWithCategory(
            @RequestBody List<CreateQuestionAndCategoryRequestDto> requestDtos
    ) {
        questionService.recursiveCreateQuestionChoice(requestDtos);
    }

    @GetMapping("question/{questionId}")
    @ResponseStatus(HttpStatus.OK)
    public QuestionResponseDto haha(
            @PathVariable Long questionId
    ) {
        return questionService.findQuestionWithChoiceAndCategory(questionId);
    }

    @PostMapping("question/{questionId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void choiceQuestion(
            @PathVariable Long questionId,
            @RequestBody ChoiceAnswerRequestDto choiceNumber,
            @IfLogin LoginUserDto loginUserDto
    ) {
        questionService.choiceQuestion(loginUserDto, questionId, choiceNumber);
    }

    @GetMapping("questions")
    @ResponseStatus(HttpStatus.OK)
    public Page<QuestionPageWithCategoryAndTitle> findQuestionPageWithCategoryAndTitleConditionalSearch(
            QuestionSearchCondition searchCondition,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size
    ) {
        return questionService.questionPageWithCategory(searchCondition, page, size);
    }
}
