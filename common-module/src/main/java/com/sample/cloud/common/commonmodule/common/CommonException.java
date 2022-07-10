package com.cloud.sample.boardservice.common;

import com.cloud.sample.boardservice.common.dto.ErrorCode;

public class CommonException extends RuntimeException {
    
    private String customMessage;
    private ErrorCode errorCode;

    /**
     * 사용자 정의 메시지를 받아 처리하는 경우
     *
     * @param errorCode 400 에러
     * @param customMessage 사용자에게 표시할 메시지
     */
    public CommonException(ErrorCode errorCode, String customMessage) {
        super(customMessage);
        this.errorCode = errorCode;
        this.customMessage = customMessage;
    }

    /**
     * 사전 정의된 에러코드 객체를 넘기는 경우
     *
     * @param message 서버에 남길 메시지
     * @param errorCode 사전 정의된 에러코드
     */
    public CommonException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * 사전 정의된 에러코드의 메시지를 서버에 남기고 에러코드 객체를 리턴한다
     * @param errorCode 사전 정의된 에러코드
     */
    public CommonException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getCustomMessage() {
        return customMessage;
    }
}