package com.kakao.search.apiservice.error.exception;

import com.kakao.search.apiservice.error.ErrorCode;

public class InvalidValueException extends BusinessException {

    public InvalidValueException(String message) {
        super(message, ErrorCode.INVALID_INPUT_VALUE);
    }

    public InvalidValueException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
