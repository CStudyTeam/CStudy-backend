package com.CStudy.global.exception.Question;

public class NotFoundQuestionId extends RuntimeException {

    public NotFoundQuestionId(Long id) {
        super("Not Found Question with: " + id);
    }

}
