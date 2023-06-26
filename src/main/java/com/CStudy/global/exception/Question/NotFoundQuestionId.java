package com.CStudy.global.exception.Question;

import com.CStudy.global.exception.QuestionAbstractException;

public class NotFoundQuestionId extends QuestionAbstractException {

    public NotFoundQuestionId(Long id) {
        super("Not Found Question with: " + id);
    }

    public NotFoundQuestionId(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public int getStatusCode() {
        return 3001;
    }


}
