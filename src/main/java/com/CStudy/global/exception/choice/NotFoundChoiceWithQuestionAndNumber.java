package com.CStudy.global.exception.choice;

public class NotFoundChoiceWithQuestionAndNumber extends RuntimeException {

    public NotFoundChoiceWithQuestionAndNumber(Long questionId, int number) {
        super("Not Found Choice with questionId: " + questionId + " and number: " + number);
    }
}
