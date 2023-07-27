package com.CStudy.global.exception.notice;

import com.CStudy.global.exception.RequestAbstractException;

public class NotMatchAdminIpException extends RequestAbstractException {

  public NotMatchAdminIpException(Long id) {
    super("Not Equals Admin ID: " + id);
  }

  public NotMatchAdminIpException(String message, Throwable cause) {
    super(message, cause);
  }

  @Override
  public int getStatusCode() {
    return 6001;
  }
}
