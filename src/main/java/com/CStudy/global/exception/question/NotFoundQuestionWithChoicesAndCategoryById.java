package com.CStudy.global.exception.question;

import com.CStudy.global.exception.QuestionAbstractException;
import com.CStudy.global.exception.enums.ErrorCode;

public class NotFoundQuestionWithChoicesAndCategoryById extends QuestionAbstractException {
    public NotFoundQuestionWithChoicesAndCategoryById(Long message) {
        super("Not Found Question With Choices And Category" + message);
    }

    public NotFoundQuestionWithChoicesAndCategoryById(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public int getStatusCode() {
        return ErrorCode.NotFoundQuestionWithChoicesAndCategoryById.getErrorCode();
    }

}
