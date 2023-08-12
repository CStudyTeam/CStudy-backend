package com.CStudy.global.exception.choice;

import com.CStudy.global.exception.ChoiceAbstractException;
import com.CStudy.global.exception.enums.ErrorCode;

public class NotFoundChoiceWithQuestionAndNumber extends ChoiceAbstractException {

    public NotFoundChoiceWithQuestionAndNumber(Long questionId, int number) {
        super("Not Found Choice with questionId: " + questionId + " and number: " + number);
    }


    @Override
    public int getStatusCode() {
        return ErrorCode.NotFoundChoiceWithQuestionAndNumber.getErrorCode();
    }
}
