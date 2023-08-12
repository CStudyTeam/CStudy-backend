package com.CStudy.domain.competition.application.impl;

import com.CStudy.domain.competition.application.MemberCompetitionService;
import com.CStudy.domain.competition.dto.response.MyCompetitionRankingDto;
import com.CStudy.domain.competition.entity.Competition;
import com.CStudy.domain.competition.entity.MemberCompetition;
import com.CStudy.domain.competition.repository.CompetitionRepository;
import com.CStudy.domain.competition.repository.MemberCompetitionRepository;
import com.CStudy.domain.member.entity.Member;
import com.CStudy.domain.member.repository.MemberRepository;
import com.CStudy.global.exception.competition.DuplicateMemberWithCompetition;
import com.CStudy.global.exception.competition.NotFoundCompetitionId;
import com.CStudy.global.exception.competition.NotFoundMemberCompetition;
import com.CStudy.global.exception.competition.ParticipantsWereInvitedParticipateException;
import com.CStudy.global.exception.member.NotFoundMemberId;
import com.CStudy.global.util.LoginUserDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberCompetitionServiceImpl implements MemberCompetitionService {

    private final MemberCompetitionRepository memberCompetitionRepository;
    private final MemberRepository memberRepository;
    private final CompetitionRepository competitionRepository;

    @Override
    @Transactional
    public void joinCompetition(LoginUserDto loginUserDto, Long competitionId) {

        preventionDuplicateParticipation(loginUserDto, competitionId);

        Member member = memberRepository.findByIdForUpdateOptimistic(loginUserDto.getMemberId())
                .orElseThrow(() -> new NotFoundMemberId(loginUserDto.getMemberId()));

        Competition competition = competitionRepository.findByIdForUpdateOptimistic(competitionId)
                .orElseThrow(() -> new NotFoundCompetitionId(competitionId));

        decreaseParticipantsCountIfPossible(competition);

        MemberCompetition memberCompetition = MemberCompetition.builder()
                .member(member)
                .competition(competition)
                .build();

        memberCompetitionRepository.save(memberCompetition);
    }

    @Override
    public int getJoinMemberCount(Long competitionId) {
        List<MemberCompetition> memberCompetitions =
                memberCompetitionRepository.findAllWithMemberAndCompetition(competitionId);
        return memberCompetitions.size();
    }

    @Override
    public MyCompetitionRankingDto myRanking(Long memberId, Long competitionId) {

        List<Long> finishMember = memberCompetitionRepository
                .findFinishMember(competitionId);
        Integer myRank = null;
        for (int i = 0; i < finishMember.size(); i++) {
            if(finishMember.get(i) == memberId){
                myRank = i+1;
                break;
            }
        }

        return new MyCompetitionRankingDto(myRank);
    }

    private void decreaseParticipantsCountIfPossible(Competition competition) {
        if(competition.getParticipants() != 0){
            competition.decreaseParticipantsCount();
        } else {
            throw new ParticipantsWereInvitedParticipateException();
        }
    }

    private void preventionDuplicateParticipation(LoginUserDto loginUserDto, Long competitionId) {
        boolean isMemberParticipating = memberCompetitionRepository.existsByMemberIdAndCompetitionId(
                loginUserDto.getMemberId(), competitionId);

        checkMemberParticipation(loginUserDto, isMemberParticipating);
    }

    private static void checkMemberParticipation(LoginUserDto loginUserDto, boolean isMemberParticipating) {
        Optional.of(isMemberParticipating)
                .filter(participating -> participating)
                .ifPresent(participating -> {
                    throw new DuplicateMemberWithCompetition(loginUserDto.getMemberId());
                });
    }
}
