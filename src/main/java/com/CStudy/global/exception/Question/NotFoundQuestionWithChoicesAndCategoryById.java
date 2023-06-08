package com.CStudy.global.exception.Question;

public class NotFoundQuestionWithChoicesAndCategoryById extends RuntimeException{
    public NotFoundQuestionWithChoicesAndCategoryById(Long message) {
        super("Not Found Question With Choices And Category" + message);
    }

}
