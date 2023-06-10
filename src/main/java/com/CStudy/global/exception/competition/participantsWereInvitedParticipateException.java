package com.CStudy.global.exception.competition;

public class participantsWereInvitedParticipateException extends RuntimeException {
    public participantsWereInvitedParticipateException() {
        super("참가 가능한 인원이 초가");
    }
}
