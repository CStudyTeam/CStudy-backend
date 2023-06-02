package com.CStudy.domain.question.controller;

import com.CStudy.domain.question.application.QuestionService;
import com.CStudy.domain.question.dto.CreateQuestionAndCategoryRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/question")
    @ResponseStatus(HttpStatus.CREATED)
    public void createQuestionWithCategory(
            @RequestBody CreateQuestionAndCategoryRequestDto requestDto
    ) {
        questionService.createQuestionChoice(requestDto);
    }
}
