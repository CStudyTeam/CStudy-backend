package com.CStudy.global.exception.workbook;

import com.CStudy.global.exception.WorkbookAbstractException;
import com.CStudy.global.exception.enums.ErrorCode;

public class NotFoundWorkbookQuestion extends WorkbookAbstractException {

    public NotFoundWorkbookQuestion() {
        super("Not Found Question in Workbook");
    }

    public NotFoundWorkbookQuestion(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public int getStatusCode() {
        return ErrorCode.NotFoundWorkbookQuestion.getErrorCode();
    }

}
