package com.CStudy.global.exception.member;

public class NotFoundMemberId extends RuntimeException {

    public NotFoundMemberId(Long memberId) {
        super("Not Found Member With:" + memberId);
    }
}
