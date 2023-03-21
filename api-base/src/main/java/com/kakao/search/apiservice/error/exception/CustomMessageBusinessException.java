package com.kakao.search.apiservice.error.exception;

import com.kakao.search.apiservice.error.ErrorCode;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomMessageBusinessException extends BusinessException {

  final Map<String, String> customMessage;

  public CustomMessageBusinessException(String message, ErrorCode errorCode,
      final Map<String, String> customMessages) {
    super(message, errorCode);
    this.customMessage = customMessages;
  }
}
