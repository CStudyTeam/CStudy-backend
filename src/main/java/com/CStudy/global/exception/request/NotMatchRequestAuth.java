package com.CStudy.global.exception.request;

import com.CStudy.global.exception.RequestAbstractException;

public class NotMatchRequestAuth extends RequestAbstractException {

    public NotMatchRequestAuth(Long message) {
        super("Not Match Auth Request" + message);
    }

    public NotMatchRequestAuth(Long message, Throwable cause) {
        super(String.valueOf(message), cause);
    }

    @Override
    public int getStatusCode() {
        return 6002;
    }
}
