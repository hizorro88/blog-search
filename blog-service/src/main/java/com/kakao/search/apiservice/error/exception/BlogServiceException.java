package com.kakao.search.apiservice.error.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class BlogServiceException extends RuntimeException {

  private BlogServiceExceptionEnum error;

  @JsonProperty("cause")
  private CauseEntity causeEntity;

  public BlogServiceException(BlogServiceExceptionEnum e) {
    super(e.getMessage());
    this.error = e;
  }

  public BlogServiceException(BlogServiceExceptionEnum e, CauseEntity causeEntity) {
    super(e.getMessage());
    this.error = e;
    this.causeEntity = causeEntity;
  }
}
