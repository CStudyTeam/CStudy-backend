package com.CStudy.global.exception.member;

import com.CStudy.global.exception.MemberAbstractException;

public class InvalidMatchPasswordException extends MemberAbstractException {
    public InvalidMatchPasswordException(String message) {
        super(message + "가 일치하지 않습니다.");
    }

    public InvalidMatchPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public int getStatusCode() {
        return 1002;
    }

}
