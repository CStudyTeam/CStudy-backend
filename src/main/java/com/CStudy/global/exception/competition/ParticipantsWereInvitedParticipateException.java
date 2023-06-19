package com.CStudy.global.exception.competition;

public class ParticipantsWereInvitedParticipateException extends RuntimeException {
    public ParticipantsWereInvitedParticipateException() {
        super("참가 가능한 인원이 초과");
    }
}
