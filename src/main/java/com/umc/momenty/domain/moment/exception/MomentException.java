package com.umc.momenty.domain.moment.exception;

import com.umc.momenty.global.apiPayload.code.BaseErrorCode;
import com.umc.momenty.global.apiPayload.exception.GeneralException;

public class MomentException extends GeneralException {
    public MomentException(BaseErrorCode code) {
        super(code);
    }
}
