package com.CStudy.global.exception.member;

import com.CStudy.global.exception.MemberAbstractException;
import com.CStudy.global.exception.enums.ErrorCode;

public class NotFoundMemberId extends MemberAbstractException {
    public NotFoundMemberId(Long message) {
        super("Not Found Member With:" + message);
    }

    public NotFoundMemberId(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public int getStatusCode() {
        return ErrorCode.NotFoundMemberId.getErrorCode();
    }
}
