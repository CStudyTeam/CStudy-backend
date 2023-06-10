package com.CStudy.global.exception.competition;

public class DuplicateMemberWithCompetition extends RuntimeException {
    public DuplicateMemberWithCompetition(Long memberId) {
        super("Duplicate request sent for the same competition."+ memberId);
    }
}
