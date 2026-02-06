package com.umc.momenty.domain.support.exception;

import com.umc.momenty.global.apiPayload.code.BaseErrorCode;
import com.umc.momenty.global.apiPayload.exception.GeneralException;

public class FaqException extends GeneralException {
    public FaqException(BaseErrorCode code) {
        super(code);
    }
}
