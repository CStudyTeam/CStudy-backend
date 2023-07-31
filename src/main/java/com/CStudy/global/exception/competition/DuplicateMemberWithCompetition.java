package com.CStudy.global.exception.competition;

import com.CStudy.global.exception.CompetitionAbstractException;
import com.CStudy.global.exception.enums.ErrorCode;

public class DuplicateMemberWithCompetition extends CompetitionAbstractException {

    public DuplicateMemberWithCompetition(Long memberId) {
        super("Duplicate request sent for the same competition." + memberId);
    }

    public DuplicateMemberWithCompetition(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public int getStatusCode() {
        return ErrorCode.DuplicateMemberWithCompetition.getErrorCode();
    }

}
