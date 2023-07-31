package com.CStudy.global.exception.notice;

import com.CStudy.global.exception.RequestAbstractException;
import com.CStudy.global.exception.enums.ErrorCode;

public class NotMatchAdminIpException extends RequestAbstractException {

  public NotMatchAdminIpException(Long id) {
    super("Not Equals Admin ID: " + id);
  }

  public NotMatchAdminIpException(String message, Throwable cause) {
    super(message, cause);
  }

  @Override
  public int getStatusCode() {
    return ErrorCode.NotMatchAdminIp.getErrorCode();
  }
}
