package com.CStudy.global.exception.workbook;

public class NotFoundWorkbookQuestion extends RuntimeException{

    public NotFoundWorkbookQuestion() {
        super("Not Found Question in Workbook");
    }
}
