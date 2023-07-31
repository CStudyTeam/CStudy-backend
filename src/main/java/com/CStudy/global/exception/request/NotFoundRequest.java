package com.CStudy.global.exception.request;

import com.CStudy.global.exception.RequestAbstractException;
import com.CStudy.global.exception.enums.ErrorCode;

public class NotFoundRequest extends RequestAbstractException {

  public NotFoundRequest(Long id) {
    super("Not Found Request With: " + id);
  }

  public NotFoundRequest(String message, Throwable cause) {
    super(message, cause);
  }

  @Override
  public int getStatusCode() {
    return ErrorCode.NotFoundRequest.getErrorCode();
  }
}
