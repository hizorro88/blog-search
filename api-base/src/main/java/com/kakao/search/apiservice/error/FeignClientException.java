package com.kakao.search.apiservice.error;

import java.util.Collection;
import java.util.Map;
import lombok.Getter;

@Getter
public class FeignClientException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private final int status;
  private final String errorMessage;

  private final Map<String, Collection<String>> headers;

  public FeignClientException(Integer status, String errorMessage,
      Map<String, Collection<String>> headers) {
    super(errorMessage);
    this.status = status;
    this.errorMessage = errorMessage;
    this.headers = headers;

  }
}