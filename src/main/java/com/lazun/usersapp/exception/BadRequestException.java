package com.lazun.usersapp.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ApiException {
  private static final long serialVersionUID = 1L;

  private static final String BAD_REQUEST_ERROR = "bad_request";

  public BadRequestException(Throwable e) {
    super(
        BAD_REQUEST_ERROR,
        HttpStatus.BAD_REQUEST.getReasonPhrase(),
        HttpStatus.BAD_REQUEST.value(),
        e);
  }

  public BadRequestException(String message) {
    super(BAD_REQUEST_ERROR, message, HttpStatus.BAD_REQUEST.value());
  }

  public BadRequestException(String message, Throwable e) {
    super(BAD_REQUEST_ERROR, message, HttpStatus.BAD_REQUEST.value(), e);
  }
}
