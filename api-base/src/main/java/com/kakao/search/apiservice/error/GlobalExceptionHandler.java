package com.kakao.search.apiservice.error;

import com.kakao.search.apiservice.error.exception.BusinessException;
import com.kakao.search.apiservice.error.exception.CustomMessageBusinessException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.RejectedExecutionException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.validator.internal.engine.path.NodeImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다. HttpMessageConverter 에서 등록한
   * HttpMessageConverter binding 못할경우 발생 주로 @RequestBody, @RequestPart 어노테이션에서 발생
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    log.trace("handleMethodArgumentNotValidException", e);
    final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE,
        e.getBindingResult());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }


  /**
   * @ModelAttribut 으로 binding error 발생시 BindException 발생한다. ref
   * https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-modelattrib-method-args
   */
  @ExceptionHandler(BindException.class)
  protected ResponseEntity<ErrorResponse> handleBindException(BindException e) {
    log.trace("handleBindException", e);
    final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE,
        e.getBindingResult());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }


  /**
   * enum type 일치하지 않아 binding 못할 경우 발생 주로 @RequestParam enum으로 binding 못했을 경우 발생
   */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException e) {
    log.trace("handleMethodArgumentTypeMismatchException", e);
    final ErrorResponse response = ErrorResponse.of(e);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }


  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException e) {
    log.trace("handleHttpRequestMethodNotSupportedException", e);
    final ErrorResponse response = ErrorResponse.of(ErrorCode.METHOD_NOT_ALLOWED);
    return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
  }


  @ExceptionHandler(NoHandlerFoundException.class)
  protected ResponseEntity<ErrorResponse> handleNotFoundException(NoHandlerFoundException e) {
    log.trace("handleNotFoundException", e);
    final ErrorResponse response = ErrorResponse.of(ErrorCode.RESOURCE_NOT_FOUND);
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }


  @ExceptionHandler(AccessDeniedException.class)
  protected ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
    log.trace("handleAccessDeniedException", e);
    final ErrorResponse response = ErrorResponse.of(ErrorCode.HANDLE_ACCESS_DENIED);
    return new ResponseEntity<>(response,
        HttpStatus.valueOf(ErrorCode.HANDLE_ACCESS_DENIED.getStatus()));
  }

  @ExceptionHandler(CustomMessageBusinessException.class)
  protected ResponseEntity<ErrorResponse> handleCustomMessageBusinessException(
      final CustomMessageBusinessException e) {
    if (e.getErrorCode() == ErrorCode.INTERNAL_SERVER_ERROR) {
      log.error("handleCustomMessageBusinessException", e);
    } else {
      log.trace("handleCustomMessageBusinessException", e);
    }
    final ErrorCode errorCode = e.getErrorCode();
    final ErrorResponse response = ErrorResponse.of(errorCode);
    response.setMessage(e.getMessage());
    response.setData(e.getCustomMessage());
    return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
  }

  @ExceptionHandler(BusinessException.class)
  protected ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException e) {
    if (e.getErrorCode() == ErrorCode.INTERNAL_SERVER_ERROR) {
      log.error("handleBusinessException", e);
    } else {
      log.trace("handleBusinessException", e);
    }
    final ErrorCode errorCode = e.getErrorCode();
    final ErrorResponse response = ErrorResponse.of(errorCode);
    response.setMessage(e.getMessage());
    return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  protected ResponseEntity<ErrorResponse> handleIllegalArgumentException(
      final IllegalArgumentException e) {
    log.trace("IllegalArgumentException", e);
    final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE);
    response.setMessage(e.getMessage());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  protected ResponseEntity<ErrorResponse> handleConstraintViolationException(
      ConstraintViolationException e) {
    log.trace("handleConstraintViolationException", e);
    final Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
    final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_TYPE_VALUE);
    if (violations != null) {
      final List<ErrorResponse.FieldError> fieldErrorList = new ArrayList<>();
      violations.forEach(violation -> {
        fieldErrorList.add(new ErrorResponse.FieldError(
            getNodePathName(violation.getPropertyPath(), WhatIsNodeInPath.LAST),
            violation.getInvalidValue() != null ? violation.getInvalidValue().toString()
                : null, violation.getMessage()));
      });
      if (CollectionUtils.isNotEmpty(fieldErrorList)) {
        response.setErrors(fieldErrorList);
      } else {
        response.setMessage(e.getLocalizedMessage());
      }
    }
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  protected ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(
      MissingServletRequestParameterException e) {
    log.trace("handleMissingServletRequestParameterException", e);
    final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_TYPE_VALUE);
    response.setErrors(Arrays.asList(
        new ErrorResponse.FieldError(e.getParameterName(), null, "필수 값이 누락되었습니다.")));
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MissingRequestHeaderException.class)
  protected ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(
      MissingRequestHeaderException e) {
    log.trace("handleMissingRequestHeaderException", e);
    final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_TYPE_VALUE);
    response.setMessage("필수 헤더가 존재하지 않습니다. (" + e.getMessage() + ")");
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MissingServletRequestPartException.class)
  protected ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(
      MissingServletRequestPartException e) {
    log.trace("handleMissingServletRequestPartException", e);
    final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_TYPE_VALUE);
    response.setMessage("필수 파트가 존재하지 않습니다. (" + e.getMessage() + ")");
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  protected ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(
      HttpMessageNotReadableException e) {
    log.trace("handleHttpMessageNotReadableException", e);
    final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_TYPE_VALUE);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(RejectedExecutionException.class)
  protected ResponseEntity<ErrorResponse> handleRejectedExecutionException(
      RejectedExecutionException e) {
    log.trace("handleAsyncRequestTimeoutException", e);
    final ErrorResponse response = ErrorResponse.of(ErrorCode.SERVICE_UNAVAILABLE);
    return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
  }

  @ExceptionHandler(AsyncRequestTimeoutException.class)
  protected ResponseEntity<ErrorResponse> handleAsyncRequestTimeoutException(
      AsyncRequestTimeoutException e) {
    log.trace("handleAsyncRequestTimeoutException", e);
    final ErrorResponse response = ErrorResponse.of(ErrorCode.SERVICE_UNAVAILABLE);
    return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<ErrorResponse> handleException(Exception e) {
    log.error("handleException", e);
    final ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private String getNodePathName(final Path path, final WhatIsNodeInPath whatIsNodeInPath) {
    if (whatIsNodeInPath == WhatIsNodeInPath.LAST) {
      if (path instanceof PathImpl) {
        final PathImpl pathImpl = (PathImpl) path;
        final NodeImpl leafNode = pathImpl.getLeafNode();
        if (leafNode != null) {
          return leafNode.getName();
        }
      }
    }
    return null;
  }

  private enum WhatIsNodeInPath {LAST}

  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  protected ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupportedException(
      HttpMediaTypeNotSupportedException e) {
    log.trace("handleHttpMediaTypeNotSupportedException", e);
    final ErrorResponse response = ErrorResponse.of(ErrorCode.UNSUPPORTED_MEDIA_TYPE);
    response.setMessage(e.getMessage());
    return new ResponseEntity<>(response, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
  }
}
