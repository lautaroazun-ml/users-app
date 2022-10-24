package com.lazun.usersapp.exception;

import org.springframework.http.HttpStatus;

public class AlreadyExistException extends ApiException {
  private static final long serialVersionUID = 1L;

  private static final String ALREADY_EXISTS = "already_exists";

  public AlreadyExistException(Throwable e) {
    super(
        ALREADY_EXISTS,
        HttpStatus.ALREADY_REPORTED.getReasonPhrase(),
        HttpStatus.BAD_REQUEST.value(),
        e);
  }

  public AlreadyExistException(String message) {
    super(ALREADY_EXISTS, message, HttpStatus.FORBIDDEN.value());
  }

  public AlreadyExistException(String message, Throwable e) {
    super(ALREADY_EXISTS, message, HttpStatus.FORBIDDEN.value(), e);
  }
}
