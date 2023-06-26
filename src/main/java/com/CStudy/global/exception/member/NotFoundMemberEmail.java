package com.CStudy.global.exception.member;


import com.CStudy.global.exception.MemberAbstractException;

public class NotFoundMemberEmail extends MemberAbstractException {

    public NotFoundMemberEmail(String message) {
        super("User email is not found : " + message);
    }

    public NotFoundMemberEmail(String message, Throwable cause) {
        super(message, cause);
    }


    @Override
    public int getStatusCode() {
        return 1003;
    }
}
