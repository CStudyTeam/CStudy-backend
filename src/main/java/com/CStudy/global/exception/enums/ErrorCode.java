package com.CStudy.global.exception.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {

    NotFoundMemberId("1111"),//회원 아이디를 찾을 수 없습니다.
    EmailDuplication("1112"),//회원가입 이메일 중복이 되었습니다.
    MethodArgumentNotValidException("9999");//Valid에 적합하지 않습니다.

    private final String errorCode;


    ErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

}
