package com.kakao.search.apiservice.error.exception;


import com.kakao.search.apiservice.error.ErrorCode;

public class ServerErrorException extends BusinessException {

    public ServerErrorException(String message) {
        super(message, ErrorCode.INTERNAL_SERVER_ERROR);
    }

    public ServerErrorException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
