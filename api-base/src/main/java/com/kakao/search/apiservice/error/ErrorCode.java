package com.kakao.search.apiservice.error;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

  // Common
  INVALID_INPUT_VALUE(400, "C001", "입력 데이터에 문제가 있습니다."),
  METHOD_NOT_ALLOWED(405, "C002", "지원되지 않습니다"),
  ENTITY_NOT_FOUND(400, "C003", "결과가 존재하지 않습니다."),
  INTERNAL_SERVER_ERROR(500, "C004", "서버 처리 오류(관리자에게 문의하세요)"),
  INVALID_TYPE_VALUE(400, "C005", "입력 데이터에 문제가 있습니다."),
  HANDLE_ACCESS_DENIED(403, "C006", "접근이 허용되지 않습니다."),
  RESOURCE_NOT_FOUND(404, "C007", "리소스가 존재하지 않습니다."),
  UNAUTHORIZED(401, "C008", "권한이 없습니다."),
  SERVICE_UNAVAILABLE(503, "C009", "지원되지 않는 서비스입니다."),
  UNSUPPORTED_MEDIA_TYPE(415, "C010", "지원하지 않는 Media Type입니다.");

  private final int status;
  private final String code;
  private final String message;

  ErrorCode(final int status, final String code, final String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }

  public int getStatus() {
    return status;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return this.message;
  }

}
