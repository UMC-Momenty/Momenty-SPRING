package com.umc.momenty.domain.support.exception;

import com.umc.momenty.global.apiPayload.code.BaseErrorCode;
import com.umc.momenty.global.apiPayload.exception.GeneralException;

public class NoticeException extends GeneralException {
    public NoticeException(BaseErrorCode code) {
        super(code);
    }
}
