package com.kakao.search.apiservice.error.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kakao.search.apiservice.error.ErrorResponse.FieldError;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CauseEntity {

  private String from;
  private Integer status;
  private String code;
  private final String message;
  private List<FieldError> errors;
  private Map<String, Object> payloads;
}
