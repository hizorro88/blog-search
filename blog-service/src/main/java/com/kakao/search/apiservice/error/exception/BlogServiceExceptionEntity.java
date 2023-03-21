package com.kakao.search.apiservice.error.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kakao.search.apiservice.error.ErrorResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlogServiceExceptionEntity extends ErrorResponse {

  private int status;
  private String code;
  private String message;
  private CauseEntity cause;
}
