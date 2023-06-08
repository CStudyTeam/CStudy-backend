package com.CStudy.global.exception.member;

public class InvalidMatchPasswordException extends RuntimeException {
    public InvalidMatchPasswordException(String message) {
        super(message+"가 일치하지 않습니다.");
    }
}
