package com.CStudy.global.exception.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {

    NotFoundMemberId("1000"),//회원 아이디를 찾을 수 없습니다.
    EmailDuplication("1001"),//회원가입 이메일 중복이 되었습니다.
    InvalidMatchPasswordException("1002"),//로그인 비밀번호가 일치하지 않습니다.
    NotFoundCategoryTile("2001"),//카테고리 제목을 찾을 수 없습니다.
    NotFoundWorkbook("4001"), //문제집을 찾을 수 없습니다.
    NotFoundWorkbookQuestion("4002"), // 문제집에서 해당 문제를 찾을 수 없습니다.
    NotFoundCompetitionId("5001"), //대회 정보를 찾을 수 없습니다.
    NotFoundMemberCompetition("5002"), // 해당 대회에 등록한 회원이 없습니다.
    participantsWereInvitedParticipateException("5003"), //참여가능한 인원이 초과되었습니다.
    DuplicateMemberWithCompetition("5004"), //대회에 이미 참여한 인원입니다.
    NotFoundRequest("6001"), //게시글을 찾을 수 없습니다.
    MethodArgumentNotValidException("9999");//Valid에 적합하지 않습니다.

    private final String errorCode;


    ErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

}
