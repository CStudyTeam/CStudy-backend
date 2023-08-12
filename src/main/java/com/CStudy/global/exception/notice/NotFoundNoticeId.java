package com.CStudy.global.exception.notice;

import com.CStudy.global.exception.RequestAbstractException;
import com.CStudy.global.exception.enums.ErrorCode;

public class NotFoundNoticeId extends RequestAbstractException {

  public NotFoundNoticeId(Long id) {
    super("Not Found Notice Id: " + id);
  }

  public NotFoundNoticeId(String message, Throwable cause) {
    super(message, cause);
  }

  @Override
  public int getStatusCode() {
    return ErrorCode.NotFoundNoticeId.getErrorCode();
  }
}
