package com.CStudy.global.exception.competition;

import com.CStudy.global.exception.CompetitionAbstractException;
import com.CStudy.global.exception.enums.ErrorCode;

public class ParticipantsWereInvitedParticipateException extends CompetitionAbstractException {

    public ParticipantsWereInvitedParticipateException() {
        super("참가 가능한 인원이 초과");
    }

    public ParticipantsWereInvitedParticipateException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public int getStatusCode() {
        return ErrorCode.participantsWereInvitedParticipateException.getErrorCode();
    }
}
