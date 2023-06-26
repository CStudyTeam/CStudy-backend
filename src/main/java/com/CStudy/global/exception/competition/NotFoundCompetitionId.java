package com.CStudy.global.exception.competition;

import com.CStudy.global.exception.CompetitionAbstractException;

public class NotFoundCompetitionId extends CompetitionAbstractException {
    public NotFoundCompetitionId(Long competitionId) {
        super("Not Found Competition. CompetitionId: "+competitionId);
    }

    public NotFoundCompetitionId(String message, Throwable cause) {
        super(message, cause);
    }


    @Override
    public int getStatusCode() {
        return 5001;
    }
}
