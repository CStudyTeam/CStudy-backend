package com.CStudy.global.exception.member;

import com.CStudy.global.exception.MemberAbstractException;

public class NotFoundMemberId extends MemberAbstractException {
    public NotFoundMemberId(Long message) {
        super("Not Found Member With:" + message);
    }

    public NotFoundMemberId(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public int getStatusCode() {
        return 1000;
    }
}
