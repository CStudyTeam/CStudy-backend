package com.CStudy.global.exception.competition;

import com.CStudy.global.exception.CompetitionAbstractException;
import com.CStudy.global.exception.enums.ErrorCode;

public class CompetitionFinishedException extends CompetitionAbstractException {


    public CompetitionFinishedException() {
        super("Competition Already Finished");
    }

    public CompetitionFinishedException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public int getStatusCode() {
        return ErrorCode.CompetitionFinishedException.getErrorCode();
    }
}
