package com.cloud.sample.boardservice.common;

import com.cloud.sample.boardservice.common.dto.ErrorCode;

public class CommonMessageException extends CommonException {

    /**
     * 사용자에게 표시될 메시지와 상태코드 400 을 넘긴다
     *
     * @param customMessage
     */
    public CommonMessageException(String customMessage) {
        super(ErrorCode.BUSINESS_CUSTOM_MESSAGE, customMessage);
    }

}