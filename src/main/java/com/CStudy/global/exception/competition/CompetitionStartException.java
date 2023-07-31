package com.CStudy.global.exception.competition;

import com.CStudy.global.exception.CompetitionAbstractException;
import com.CStudy.global.exception.enums.ErrorCode;

public class CompetitionStartException extends CompetitionAbstractException {


    public CompetitionStartException() {
        super("Competition Start Time Exception");
    }

    public CompetitionStartException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public int getStatusCode() {
        return ErrorCode.CompetitionStartException.getErrorCode();
    }
}
