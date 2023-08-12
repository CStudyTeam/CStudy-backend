package com.CStudy.global.exception.workbook;

import com.CStudy.global.exception.WorkbookAbstractException;
import com.CStudy.global.exception.enums.ErrorCode;

public class NotFoundWorkbook extends WorkbookAbstractException {

    public NotFoundWorkbook(Long id) {
        super("Not Found Workbook: " + id);
    }

    public NotFoundWorkbook(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public int getStatusCode() {
        return ErrorCode.NotFoundWorkbook.getErrorCode();
    }

}
