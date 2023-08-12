package com.CStudy.domain.competition.application;

import com.CStudy.domain.competition.dto.response.MyCompetitionRankingDto;
import com.CStudy.global.util.LoginUserDto;

public interface MemberCompetitionService {
     void joinCompetition(LoginUserDto loginUserDto , Long competitionId);

     int getJoinMemberCount(Long competitionId);

     MyCompetitionRankingDto myRanking(Long memberId, Long competitionId);
}
