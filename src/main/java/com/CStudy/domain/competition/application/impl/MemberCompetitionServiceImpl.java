package com.CStudy.domain.competition.application.impl;

import com.CStudy.domain.competition.application.MemberCompetitionService;
import com.CStudy.domain.competition.entity.Competition;
import com.CStudy.domain.competition.entity.MemberCompetition;
import com.CStudy.domain.competition.repository.CompetitionRepository;
import com.CStudy.domain.competition.repository.MemberCompetitionRepository;
import com.CStudy.domain.member.entity.Member;
import com.CStudy.domain.member.repository.MemberRepository;
import com.CStudy.global.exception.competition.DuplicateMemberWithCompetition;
import com.CStudy.global.exception.competition.NotFoundCompetitionId;
import com.CStudy.global.exception.competition.participantsWereInvitedParticipateException;
import com.CStudy.global.exception.member.NotFoundMemberId;
import com.CStudy.global.util.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

        Member member = memberRepository.findById(loginUserDto.getMemberId())
                .orElseThrow(() -> new NotFoundMemberId(loginUserDto.getMemberId()));

        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new NotFoundCompetitionId(competitionId));

        decreaseParticipantsCountIfPossible(competition);

        MemberCompetition memberCompetition = MemberCompetition.builder()
                .member(member)
                .competition(competition)
                .build();

        memberCompetitionRepository.save(memberCompetition);
    }

    private void decreaseParticipantsCountIfPossible(Competition competition) {
        if (competition.getParticipants() != 0) {
            competition.decreaseParticipantsCount();
        } else {
            throw new participantsWereInvitedParticipateException();
        }
    }

    private void preventionDuplicateParticipation(LoginUserDto loginUserDto, Long competitionId) {
        List<MemberCompetition> memberAboutCompetition =
                memberCompetitionRepository.findAllWithMemberAndCompetition(competitionId);

        List<Long> memberIdList = memberAboutCompetition.stream()
                .map(MemberCompetition::getId)
                .collect(Collectors.toList());

        if (memberIdList.contains(loginUserDto.getMemberId())) {
            throw new DuplicateMemberWithCompetition(loginUserDto.getMemberId());
        }

    }
}
