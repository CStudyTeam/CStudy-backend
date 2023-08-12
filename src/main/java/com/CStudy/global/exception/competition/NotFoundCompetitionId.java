package com.CStudy.global.exception.competition;

import com.CStudy.global.exception.CompetitionAbstractException;
import com.CStudy.global.exception.enums.ErrorCode;

public class NotFoundCompetitionId extends CompetitionAbstractException {
    public NotFoundCompetitionId(Long competitionId) {
        super("Not Found Competition. CompetitionId: "+competitionId);
    }

    public NotFoundCompetitionId(String message, Throwable cause) {
        super(message, cause);
    }


    @Override
    public int getStatusCode() {
        return ErrorCode.NotFoundCompetitionId.getErrorCode();
    }
}
