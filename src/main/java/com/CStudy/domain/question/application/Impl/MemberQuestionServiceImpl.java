package com.CStudy.domain.question.application.Impl;

import com.CStudy.domain.member.entity.Member;
import com.CStudy.domain.member.repository.MemberRepository;
import com.CStudy.domain.question.application.MemberQuestionService;
import com.CStudy.domain.question.dto.request.ChoiceAnswerRequestDto;
import com.CStudy.domain.question.dto.response.QuestionAnswerDto;
import com.CStudy.domain.question.entity.MemberQuestion;
import com.CStudy.domain.question.entity.Question;
import com.CStudy.domain.question.repository.MemberQuestionRepository;
import com.CStudy.domain.question.repository.QuestionRepository;
import com.CStudy.global.exception.member.NotFoundMemberId;
import com.CStudy.global.exception.question.existByMemberQuestionDataException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


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

    /**
     * Select a problem. If you choose the correct answer,
     * add the correct answer to the memberQuestion table.
     *
     * @param memberId               회원 아이디
     * @param questionId             단일 문제에 대한 아이디
     * @param choiceAnswerRequestDto 문제 선택
     */
    @Override
    @Transactional
    public void findMemberAndMemberQuestionSuccess(Long memberId, Long questionId, ChoiceAnswerRequestDto choiceAnswerRequestDto) {

        findByQuestionAboutMemberIdAndQuestionIdSuccess(memberId, questionId);
        findByQuestionAboutMemberIdAndQuestionIdFail(memberId, questionId);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberId(memberId));

        Question question = questionRepository.findById(questionId)
                .orElseThrow();


        if (memberQuestionRepository.existsByMemberAndQuestionAndSuccess(memberId, questionId, choiceAnswerRequestDto.getChoiceNumber())) {
            throw new existByMemberQuestionDataException(memberId, questionId, choiceAnswerRequestDto.getChoiceNumber());
        }

        member.addRankingPoint(choiceAnswerRequestDto);

        memberQuestionRepository.save(MemberQuestion.builder()
                .member(member)
                .question(question)
                .success(choiceAnswerRequestDto.getChoiceNumber())
                .solveTime(choiceAnswerRequestDto.getTime())
                .build());
    }

    /**
     * Select a problem. If you choose the correct answer,
     * add the wrong answer to the memberQuestion table.
     *
     * @param memberId               회원 아이디
     * @param questionId             단일 문제에 대한 아이디
     * @param choiceAnswerRequestDto 선택 문제
     */
    @Override
    @Transactional
    public void findMemberAndMemberQuestionFail(Long memberId, Long questionId, ChoiceAnswerRequestDto choiceAnswerRequestDto) {

        findByQuestionAboutMemberIdAndQuestionIdSuccess(memberId, questionId);
        findByQuestionAboutMemberIdAndQuestionIdFail(memberId, questionId);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberId(memberId));

        Question question = questionRepository.findById(questionId)
                .orElseThrow();

        if (memberQuestionRepository.existsByMemberAndQuestionAndFail(memberId, questionId, choiceAnswerRequestDto.getChoiceNumber())) {
            throw new existByMemberQuestionDataException(memberId, questionId, choiceAnswerRequestDto.getChoiceNumber());
        }

        member.minusRankingPoint(member.getRankingPoint());

        memberQuestionRepository.save(MemberQuestion.builder()
                .member(member)
                .question(question)
                .fail(choiceAnswerRequestDto.getChoiceNumber())
                .solveTime(choiceAnswerRequestDto.getTime())
                .build());
    }


    /**
     * If the memberQuestion table contains information
     * that is an existing incorrect answer, delete the incorrect answer and add the correct answer.
     *
     * @param memberId   회원 아이디
     * @param questionId 단일 문제에 대한 아이디
     */
    @Override
    @Transactional
    public void findByQuestionAboutMemberIdAndQuestionIdSuccess(Long memberId, Long questionId) {
        long count = memberQuestionRepository.countByMemberIdAndQuestionIdAndSuccessZero(memberId, questionId);
        if (count != 0) {
            Optional<MemberQuestion> questionOptional = memberQuestionRepository.findByQuestionAboutMemberIdAndQuestionId(memberId, questionId);
            questionOptional.ifPresent(question -> memberQuestionRepository.deleteById(question.getId()));
            questionOptional.orElseThrow(() -> new RuntimeException("MemberQuestion not found"));
        }
    }

    /**
     * If the memberQuestion table contains information
     * that is an existing correct answer, delete the correct answer and add the correct answer.
     *
     * @param memberId   회원 아이디
     * @param questionId 단일 문제에 대한 아이디
     */
    @Override
    @Transactional
    public void findByQuestionAboutMemberIdAndQuestionIdFail(Long memberId, Long questionId) {
        long count = memberQuestionRepository.countByMemberIdAndQuestionIdAndFailZero(memberId, questionId);
        if (count != 0) {
            Optional<MemberQuestion> questionOptional = memberQuestionRepository.findByQuestionAboutMemberIdAndQuestionId(memberId, questionId);
            questionOptional.ifPresent(question -> memberQuestionRepository.deleteById(question.getId()));
            questionOptional.orElseThrow(() -> new RuntimeException("MemberQuestion not found"));
        }
    }

    @Override
    public QuestionAnswerDto isCorrectAnswer(Long memberId, Long questionId, ChoiceAnswerRequestDto requestDto) {
        boolean answer = memberQuestionRepository.existsByMemberAndQuestionAndSuccess(memberId, questionId, requestDto.getChoiceNumber());
        return QuestionAnswerDto.builder()
                .answer(answer)
                .build();
    }
}
