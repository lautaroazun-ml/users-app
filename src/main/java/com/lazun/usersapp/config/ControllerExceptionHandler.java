package com.lazun.usersapp.config;

import com.lazun.usersapp.exception.ApiError;
import com.lazun.usersapp.exception.ApiException;
import com.lazun.usersapp.exception.BadRequestException;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {
  private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<ApiError> noHandlerFoundException(
      HttpServletRequest req, NoHandlerFoundException ex) {
    ApiError apiError =
        new ApiError(
            "route_not_found",
            String.format("Route %s not found", req.getRequestURI()),
            HttpStatus.NOT_FOUND.value());
    return ResponseEntity.status(apiError.getStatus()).body(apiError);
  }

  @ExceptionHandler(value = {ApiException.class})
  protected ResponseEntity<ApiError> handleApiException(ApiException e) {
    Integer statusCode = e.getStatusCode();
    boolean expected = HttpStatus.INTERNAL_SERVER_ERROR.value() > statusCode;
    if (expected) {
      LOGGER.warn("Internal Api warn. Status Code: " + statusCode, e);
    } else {
      LOGGER.error("Internal Api error. Status Code: " + statusCode, e);
    }

    ApiError apiError = new ApiError(e.getCode(), e.getDescription(), statusCode);
    return ResponseEntity.status(apiError.getStatus()).body(apiError);
  }

  @ExceptionHandler(value = {BadRequestException.class})
  protected ResponseEntity<ApiError> handleBadRequestException(BadRequestException e) {
    ApiError apiError =
        new ApiError("bad_request", e.getDescription(), HttpStatus.BAD_REQUEST.value());
    return ResponseEntity.status(apiError.getStatus()).body(apiError);
  }

  @ExceptionHandler(value = {Exception.class})
  protected ResponseEntity<ApiError> handleUnknownException(Exception e) {
    LOGGER.error("Internal error", e);

    ApiError apiError =
        new ApiError(
            "internal_error",
            String.format("Internal server error: %s", e.getMessage()),
            HttpStatus.INTERNAL_SERVER_ERROR.value());
    return ResponseEntity.status(apiError.getStatus()).body(apiError);
  }

  @ExceptionHandler(value = {MethodArgumentNotValidException.class})
  public ResponseEntity<ApiError> methodArgumentNotValidException(
      MethodArgumentNotValidException e) {

    String errorMsg =
        e.getBindingResult().getFieldErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.joining(".\n"));

    ApiError apiError = new ApiError("bad_request", errorMsg, HttpStatus.BAD_REQUEST.value());
    return ResponseEntity.status(apiError.getStatus()).body(apiError);
  }

  @ExceptionHandler(value = {ConstraintViolationException.class})
  public ResponseEntity<ApiError> constraintViolationExceptionException(
      ConstraintViolationException e) {

    Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
    String errorMsg = "";
    if (!violations.isEmpty()) {
      StringBuilder builder = new StringBuilder();
      violations.forEach(violation -> builder.append(violation.getMessage() + "\n "));
      errorMsg = builder.toString();
    } else {
      errorMsg = "ConstraintViolationException occured.";
    }

    ApiError apiError = new ApiError("bad_request", errorMsg, HttpStatus.BAD_REQUEST.value());
    return ResponseEntity.status(apiError.getStatus()).body(apiError);
  }
}
