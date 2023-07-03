package com.CStudy.domain.competition.application.impl;

import com.CStudy.domain.competition.application.MemberCompetitionService;
import com.CStudy.global.util.LoginUserDto;
import org.springframework.stereotype.Service;

@Service
public class OptimisticFacade {
    private final MemberCompetitionService memberCompetitionService;

    public OptimisticFacade(MemberCompetitionService memberCompetitionService) {
        this.memberCompetitionService = memberCompetitionService;
    }

    public void joinCompetition(LoginUserDto loginUserDto, Long competitionId) throws InterruptedException {
        while (true) {
            try {
                memberCompetitionService.joinCompetition(loginUserDto, competitionId);
                break;
            } catch (Exception e) {
                Thread.sleep(50);
            }
        }
    }
}
