package com.CStudy.global.exception.competition;

import com.CStudy.global.exception.CompetitionAbstractException;

public class NotFoundMemberCompetition extends CompetitionAbstractException {

    public NotFoundMemberCompetition() {
        super("Not Found MemberCompetition");
    }

    public NotFoundMemberCompetition(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public int getStatusCode() {
        return 5002;
    }
}
