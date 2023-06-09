package com.CStudy.global.exception.workbook;

public class NotFoundWorkbook extends RuntimeException {

    public NotFoundWorkbook(Long id){
        super("Not Found Workbook: " + id);
    }
}
