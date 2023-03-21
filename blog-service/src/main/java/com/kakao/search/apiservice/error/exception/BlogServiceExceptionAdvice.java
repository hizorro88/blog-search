package com.kakao.search.apiservice.error.exception;

import feign.RetryableException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class BlogServiceExceptionAdvice {

  @ExceptionHandler({BlogServiceException.class})
  public ResponseEntity<BlogServiceExceptionEntity> exceptionHandler(HttpServletRequest request,
      final BlogServiceException e) {
    if (e.getCauseEntity() != null) {
      return ResponseEntity.status(e.getError().getStatus())
          .body(BlogServiceExceptionEntity.builder()
              .status(e.getError().getStatus().value())
              .code(e.getError().getCode())
              .message(e.getError().getMessage())
              .cause(e.getCauseEntity())
              .build());
    }
    return ResponseEntity
        .status(e.getError().getStatus())
        .body(BlogServiceExceptionEntity.builder()
            .status(e.getError().getStatus().value())
            .code(e.getError().getCode())
            .message(e.getError().getMessage())
            .build());
  }

  @ExceptionHandler({RetryableException.class})
  public ResponseEntity<BlogServiceExceptionEntity> retryableExceptionHandler(
      HttpServletRequest request, final RetryableException e) {

    log.error("RetryableException thrown: {}", e.toString());

    return ResponseEntity.status(BlogServiceExceptionEnum.알수없는통신오류_500.getStatus())
        .body(BlogServiceExceptionEntity.builder()
            .status(BlogServiceExceptionEnum.알수없는통신오류_500.getStatus().value())
            .code(BlogServiceExceptionEnum.알수없는통신오류_500.getCode())
            .message(BlogServiceExceptionEnum.알수없는통신오류_500.getMessage())
            .build());
  }
}
