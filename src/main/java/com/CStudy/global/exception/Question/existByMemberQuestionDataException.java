package com.CStudy.global.exception.Question;

public class existByMemberQuestionDataException extends RuntimeException {
    public existByMemberQuestionDataException(String fds) {
        super(fds);
    }

    public existByMemberQuestionDataException(Long memberId, Long questionId, int choiceNumber) {
        super(memberId+"회원 아이디를 가진 사람이"+questionId+"문제에"+choiceNumber+"를 선택한 데이터가 존재");
    }
}
