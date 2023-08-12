package com.CStudy.global.exception.question;

import com.CStudy.global.exception.QuestionAbstractException;
import com.CStudy.global.exception.enums.ErrorCode;

public class NotFoundQuestionId extends QuestionAbstractException {

    public NotFoundQuestionId(Long id) {
        super("Not Found Question with: " + id);
    }

    public NotFoundQuestionId(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public int getStatusCode() {
        return ErrorCode.NotFoundQuestionId.getErrorCode();
    }


}
