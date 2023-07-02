package com.CStudy.domain.competition.application.impl;

import com.CStudy.domain.choice.entity.Choice;
import com.CStudy.domain.choice.repository.ChoiceRepository;
import com.CStudy.domain.competition.application.CompetitionScoreService;
import com.CStudy.domain.competition.dto.request.CompetitionScoreRequestDto;
import com.CStudy.domain.competition.dto.request.CompetitionScoreRequestDto.CompetitionAnswerRequestDto;
import com.CStudy.domain.competition.dto.response.CompetitionScoreResponseDto;
import com.CStudy.domain.competition.dto.response.CompetitionScoreResponseDto.ScoreDetail;
import com.CStudy.domain.competition.entity.CompetitionScore;
import com.CStudy.domain.competition.entity.MemberCompetition;
import com.CStudy.domain.competition.repository.CompetitionScoreRepository;
import com.CStudy.domain.competition.repository.MemberCompetitionRepository;
import com.CStudy.domain.question.entity.Question;
import com.CStudy.domain.question.repository.QuestionRepository;
import com.CStudy.global.exception.Question.NotFoundQuestionId;
import com.CStudy.global.exception.choice.NotFoundChoiceWithQuestionAndNumber;
import com.CStudy.global.exception.competition.NotFoundMemberCompetition;
import com.CStudy.global.util.LoginUserDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompetitionScoreServiceImpl implements CompetitionScoreService {

    private final CompetitionScoreRepository competitionScoreRepository;
    private final MemberCompetitionRepository memberCompetitionRepository;
    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;


    /**
     * 대회에서 제출한 답안을 채점.
     *
     * @param requestDto 제출한 답안 dto.
     * @param userDto user information.
     */
    @Override
    @Transactional
    public void scoring(CompetitionScoreRequestDto requestDto, LoginUserDto userDto) {
        MemberCompetition memberCompetition = memberCompetitionRepository
                .findByMemberIdAndCompetitionId(userDto.getMemberId(), requestDto.getCompetitionId())
                .orElseThrow(() -> new NotFoundMemberCompetition());

        int score = 0;
        memberCompetition.setEndTime(requestDto.getEndTime());

        for (CompetitionAnswerRequestDto questionDto : requestDto.getQuestions()) {
            Question question = questionRepository.findById(questionDto.getQuestionId())
                    .orElseThrow(() -> new NotFoundQuestionId(questionDto.getQuestionId()));

            CompetitionScore competitionScore = CompetitionScore.builder()
                    .memberCompetition(memberCompetition)
                    .question(question)
                    .choiceNumber(questionDto.getChoiceNumber())
                    .build();
            boolean correct = isCorrectAnswer(question, questionDto.getChoiceNumber());
            if(correct){
                competitionScore.setSuccess(true);
                score++;
            }

            competitionScoreRepository.save(competitionScore);
            memberCompetition.addCompetitionScore(competitionScore);
        }

        memberCompetition.setScore(score);
    }

    @Override
    @Transactional(readOnly = true)
    public CompetitionScoreResponseDto getAnswer(Long memberId, Long competitionId) {
        List<CompetitionScore> memberScores = competitionScoreRepository
                .findByCompetitionIdAndMemberId(memberId, competitionId);
        if(memberScores.isEmpty()){
            throw new NotFoundMemberCompetition();
        }
        List<ScoreDetail> answer = new ArrayList<>();

        int score = 0;
        for (CompetitionScore competitionScore: memberScores) {
            answer.add(ScoreDetail.builder()
                .questionId(competitionScore.getQuestion().getId())
                .choiceNumber(competitionScore.getChoiceNumber())
                .correct(competitionScore.isSuccess())
                .build());

            if(competitionScore.isSuccess()){
                score++;
            }
        }
        return CompetitionScoreResponseDto.builder()
                .score(score)
                .total(memberScores.size())
                .details(answer)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public int getScore(Long memberId, Long competitionId) {
        MemberCompetition memberCompetition = memberCompetitionRepository.findByMemberIdAndCompetitionId(memberId, competitionId)
                .orElseThrow(() -> new NotFoundMemberCompetition());
        return memberCompetition.getScore();
    }

    @Transactional(readOnly = true)
    private boolean isCorrectAnswer(Question question, int number) {
        Choice choice = choiceRepository.findByQuestionAndNumber(question, number)
                .orElseThrow(() -> new NotFoundChoiceWithQuestionAndNumber(question.getId(), number));
        return choice.isAnswer();
    }
}
