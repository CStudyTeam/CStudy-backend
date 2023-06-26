package com.CStudy.global.exception.workbook;

import com.CStudy.global.exception.WorkbookAbstractException;

public class NotFoundWorkbookQuestion extends WorkbookAbstractException {

    public NotFoundWorkbookQuestion() {
        super("Not Found Question in Workbook");
    }

    public NotFoundWorkbookQuestion(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public int getStatusCode() {
        return 4002;
    }

}
