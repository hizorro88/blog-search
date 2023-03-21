package com.kakao.search.apiservice.error.exception;

import com.kakao.search.apiservice.error.ErrorCode;

public class InvalidTokenException extends BusinessException{

    public InvalidTokenException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public InvalidTokenException(String message) {
        super(message, ErrorCode.UNAUTHORIZED);
    }
}
