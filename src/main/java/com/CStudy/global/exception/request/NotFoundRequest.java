package com.CStudy.global.exception.request;

public class NotFoundRequest extends RuntimeException{

  public NotFoundRequest(Long id) {
    super("Not Found Request With: " + id);
  }
}
