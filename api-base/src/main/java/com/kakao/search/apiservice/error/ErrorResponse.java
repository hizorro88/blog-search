package com.kakao.search.apiservice.error;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

  @JsonIgnore
  private int status;
  private String message;
  private List<FieldError> errors;
  private String code;
  private Map<String, String> data;

  private ErrorResponse(final ErrorCode code, final List<FieldError> errors) {
    this.message = code.getMessage();
    this.status = code.getStatus();
    this.errors = errors;
    this.code = code.getCode();
  }

  private ErrorResponse(final ErrorCode code) {
    this.message = code.getMessage();
    this.status = code.getStatus();
    this.code = code.getCode();
    this.errors = new ArrayList<>();
  }

  public static ErrorResponse of(final ErrorCode code, final BindingResult bindingResult) {
    return new ErrorResponse(code, FieldError.of(bindingResult));
  }

  public static ErrorResponse of(final ErrorCode code) {
    return new ErrorResponse(code);
  }

  public static ErrorResponse of(final ErrorCode code, final List<FieldError> errors) {
    return new ErrorResponse(code, errors);
  }

  public static ErrorResponse of(MethodArgumentTypeMismatchException e) {
    final String value = e.getValue() == null ? "" : e.getValue().toString();
    final List<FieldError> errors = FieldError.of(e.getName(), value, e.getErrorCode());
    return new ErrorResponse(ErrorCode.INVALID_TYPE_VALUE, errors);
  }


  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public static class FieldError {

    private String field;
    private String yourValue;
    private String reason;

    protected FieldError(final String field, final String value, final String reason) {
      this.field = field;
      this.yourValue = value;
      this.reason = reason;
    }

    public static List<FieldError> of(final String field, final String value, final String reason) {
      List<FieldError> fieldErrors = new ArrayList<>();
      fieldErrors.add(new FieldError(field, value, reason));
      return fieldErrors;
    }

    private static List<FieldError> of(final BindingResult bindingResult) {
      final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
      return fieldErrors.stream()
          .map(error -> new FieldError(
              error.getField(),
              error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
              error.getDefaultMessage()))
          .collect(Collectors.toList());
    }
  }
}
