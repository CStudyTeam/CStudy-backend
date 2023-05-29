package com.CStudy.global.exception.member;


public class EmailDuplication extends RuntimeException {

    public EmailDuplication(String message) {
        super("User email is already existed:" + message);
    }
}
