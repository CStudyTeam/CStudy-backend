package com.CStudy.domain.question.application.Impl;

import com.CStudy.domain.member.entity.Member;
import com.CStudy.domain.member.repository.MemberRepository;
import com.CStudy.domain.question.application.MemberQuestionService;
import com.CStudy.domain.question.entity.MemberQuestion;
import com.CStudy.domain.question.entity.Question;
import com.CStudy.domain.question.repository.MemberQuestionRepository;
import com.CStudy.domain.question.repository.QuestionRepository;
import com.CStudy.global.exception.Question.existByMemberQuestionDataException;
import com.CStudy.global.exception.member.NotFoundMemberId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberQuestionServiceImpl implements MemberQuestionService {

    private final MemberQuestionRepository memberQuestionRepository;
    private final MemberRepository memberRepository;
    private final QuestionRepository questionRepository;

    public MemberQuestionServiceImpl(
            MemberQuestionRepository memberQuestionRepository,
            MemberRepository memberRepository,
            QuestionRepository questionRepository
    ) {
        this.memberQuestionRepository = memberQuestionRepository;
        this.memberRepository = memberRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    @Transactional
    public void findMemberAndMemberQuestionSuccess(Long memberId, Long questionId, int choiceNumber) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberId(memberId));

        Question question = questionRepository.findById(questionId)
                .orElseThrow();

        if (memberQuestionRepository.existsByMemberAndQuestionAndSuccess(memberId, questionId, choiceNumber)) {
            throw new existByMemberQuestionDataException(memberId, questionId, choiceNumber);
        }

        memberQuestionRepository.save(MemberQuestion.builder()
                .member(member)
                .question(question)
                .success(choiceNumber)
                .build());
    }

    @Override
    @Transactional
    public void findMemberAndMemberQuestionFail(Long memberId, Long questionId, int choiceNumber) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberId(memberId));

        Question question = questionRepository.findById(questionId)
                .orElseThrow();

        if (memberQuestionRepository.existsByMemberAndQuestionAndFail(memberId, questionId, choiceNumber)) {
            throw new existByMemberQuestionDataException(memberId, questionId, choiceNumber);
        }
        memberQuestionRepository.save(MemberQuestion.builder()
                .member(member)
                .question(question)
                .fail(choiceNumber)
                .build());
    }
}
