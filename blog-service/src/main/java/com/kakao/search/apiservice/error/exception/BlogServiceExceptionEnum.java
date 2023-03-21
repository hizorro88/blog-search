package com.kakao.search.apiservice.error.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum BlogServiceExceptionEnum {

  입력오류_400(HttpStatus.BAD_REQUEST, "C400", "검색어는 필수입니다."),
  알수없는통신오류_500(HttpStatus.INTERNAL_SERVER_ERROR, "C500", "통신 오류입니다.");
  private final HttpStatus status;
  private final String code;
  private String message;

  BlogServiceExceptionEnum(HttpStatus status, String code) {
    this.status = status;
    this.code = code;
  }

  BlogServiceExceptionEnum(HttpStatus status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }
}
