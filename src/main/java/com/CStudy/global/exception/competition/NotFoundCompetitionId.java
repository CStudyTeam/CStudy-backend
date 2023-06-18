package com.CStudy.global.exception.competition;

public class NotFoundCompetitionId extends RuntimeException {
    public NotFoundCompetitionId(Long competitionId) {
        super("Not Found Competition. CompetitionId: "+competitionId);
    }
}
