package com.CStudy.domain.question.application;

import com.CStudy.domain.question.dto.request.ChoiceAnswerRequestDto;
import com.CStudy.domain.question.dto.response.QuestionAnswerDto;
import com.CStudy.global.util.LoginUserDto;
import org.springframework.transaction.annotation.Transactional;

public interface MemberQuestionService {
    void findMemberAndMemberQuestionSuccess(Long memberId, Long questionId, ChoiceAnswerRequestDto choiceAnswerRequestDto);
    void findMemberAndMemberQuestionFail(Long memberId, Long questionId, ChoiceAnswerRequestDto choiceAnswerRequestDto);
    void findByQuestionAboutMemberIdAndQuestionId(Long memberId, Long questionId);

    QuestionAnswerDto isCorrectAnswer(Long memberId, Long questionId, ChoiceAnswerRequestDto requestDto);
}
