package com.CStudy.domain.question.controller;

import com.CStudy.domain.question.application.MemberQuestionService;
import com.CStudy.domain.question.application.QuestionService;
import com.CStudy.domain.question.dto.request.ChoiceAnswerRequestDto;
import com.CStudy.domain.question.dto.request.CreateQuestionAndCategoryRequestDto;
import com.CStudy.domain.question.dto.request.QuestionSearchCondition;
import com.CStudy.domain.question.dto.response.QuestionPageWithCategoryAndTitle;
import com.CStudy.domain.question.dto.response.QuestionResponseDto;
import com.CStudy.global.util.IfLogin;
import com.CStudy.global.util.LoginUserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Question(문제 API)", description = "문제 관련 API(문제 생성, 조회)")
@RestController
@RequestMapping("/api")
public class QuestionController {

    private final QuestionService questionService;
    private final MemberQuestionService memberQuestionService;

    public QuestionController(
            QuestionService questionService,
            MemberQuestionService memberQuestionService
    ) {
        this.questionService = questionService;
        this.memberQuestionService = memberQuestionService;
    }

    @Operation(summary = "문제 생성", description = "문제 생성")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "문제 생성 성공"),
        @ApiResponse(responseCode = "400", description = "문제 생성 실패")
    })
    @PostMapping("question")
    @ResponseStatus(HttpStatus.CREATED)
    public void createQuestionWithCategory(
            @Parameter(description = "title: 문제 제목, desc: 문제 설명, explain: 문제 해설, category: 카테고리, "
                                    + "number: 선택지 번호, content: 선택지 내용, answer: 정답 선택지")
            @RequestBody CreateQuestionAndCategoryRequestDto requestDto
    ) {
        questionService.createQuestionChoice(requestDto);
    }

    @Operation(summary = "문제 여러개 생성", description = "문제 여러개 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "문제 생성 성공"),
            @ApiResponse(responseCode = "400", description = "문제 생성 실패")
    })
    @PostMapping("questions")
    @ResponseStatus(HttpStatus.CREATED)
    public void buildCreateQuestionWithCategory(
            @Parameter(description = "title: 문제 제목, desc: 문제 설명, explain: 문제 해설, category: 카테고리, "
                                    + "number: 선택지 번호, content: 선택지 내용, answer: 정답 선택지")
            @RequestBody List<CreateQuestionAndCategoryRequestDto> requestDtos
    ) {
        questionService.recursiveCreateQuestionChoice(requestDtos);
    }

    @Operation(summary = "문제 조회", description = "문제 id를 이용해 문제 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "문제 조회 성공"),
            @ApiResponse(responseCode = "400", description = "문제 조회 실패")
    })
    @GetMapping("question/{questionId}")
    @ResponseStatus(HttpStatus.OK)
    public QuestionResponseDto haha(
            @Parameter(name = "questionId", description = "문제 id", required = true)
            @PathVariable Long questionId
    ) {
        return questionService.findQuestionWithChoiceAndCategory(questionId);
    }

    @Operation(summary = "문제 채점", description = "문제 id와 고른 선택지 번호를 이용해 문제 채점")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "문제 채점 성공"),
            @ApiResponse(responseCode = "400", description = "문제 채점 실패")
    })
    @PostMapping("question/{questionId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void choiceQuestion(
            @Parameter(name = "questionId", description = "문제 id", required = true)
            @PathVariable Long questionId,
            @Parameter(description = "choiceNumber: 유저가 고른 선택지 번호")
            @RequestBody ChoiceAnswerRequestDto choiceNumber,
            @Parameter(hidden = true) @IfLogin LoginUserDto loginUserDto
    ) {
        memberQuestionService.findByQuestionAboutMemberIdAndQuestionId(loginUserDto.getMemberId(),questionId);
        questionService.choiceQuestion(loginUserDto, questionId, choiceNumber);
    }

    @Operation(summary = "문제 리스트 조회", description = "문제 리스트 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "문제 리스트 조회 성공")
    })
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
