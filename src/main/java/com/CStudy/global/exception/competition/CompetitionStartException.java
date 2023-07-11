package com.CStudy.global.exception.competition;

import com.CStudy.global.exception.CompetitionAbstractException;

public class CompetitionStartException extends CompetitionAbstractException {


    public CompetitionStartException() {
        super("Competition Start Time Exception");
    }

    public CompetitionStartException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public int getStatusCode() {
        return 5005;
    }
}
