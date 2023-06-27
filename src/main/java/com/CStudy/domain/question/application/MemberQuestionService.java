package com.CStudy.domain.question.application;

import com.CStudy.domain.question.dto.request.ChoiceAnswerRequestDto;

public interface MemberQuestionService {
    void findMemberAndMemberQuestionSuccess(Long memberId, Long questionId, ChoiceAnswerRequestDto choiceAnswerRequestDto);
    void findMemberAndMemberQuestionFail(Long memberId, Long questionId, ChoiceAnswerRequestDto choiceAnswerRequestDto);
    void findByQuestionAboutMemberIdAndQuestionId(Long memberId, Long questionId);
}
