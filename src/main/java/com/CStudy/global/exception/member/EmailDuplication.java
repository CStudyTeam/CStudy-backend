package com.CStudy.global.exception.member;


import com.CStudy.global.exception.MemberAbstractException;
import com.CStudy.global.exception.enums.ErrorCode;

public class EmailDuplication extends MemberAbstractException {

    public EmailDuplication(String message) {
        super("User email is already existed:" + message);
    }

    public EmailDuplication(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public int getStatusCode() {
        return ErrorCode.EmailDuplication.getErrorCode();
    }
}
