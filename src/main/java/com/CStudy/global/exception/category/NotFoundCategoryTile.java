package com.CStudy.global.exception.category;


import com.CStudy.global.exception.CategoryAbstractException;
import com.CStudy.global.exception.enums.ErrorCode;

public class NotFoundCategoryTile extends CategoryAbstractException {

    public NotFoundCategoryTile(String message) {
        super("Not Found Category Title" + message);
    }

    @Override
    public int getStatusCode() {
        return ErrorCode.NotFoundCategoryTile.getErrorCode();
    }
}
