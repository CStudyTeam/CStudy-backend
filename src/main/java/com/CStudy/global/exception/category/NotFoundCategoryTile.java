package com.CStudy.global.exception.category;


public class NotFoundCategoryTile extends RuntimeException {

    public NotFoundCategoryTile(String message) {
        super("Not Found Category Title" + message);
    }
}
