package com.CStudy.domain.question.application;

public interface MemberQuestionService {
    void findMemberAndMemberQuestionSuccess(Long memberId, Long questionId, int choiceNumber);
    void findMemberAndMemberQuestionFail(Long memberId, Long questionId, int choiceNumber);
    void findByQuestionAboutMemberIdAndQuestionId(Long memberId, Long questionId);
}
