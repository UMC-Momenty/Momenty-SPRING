package com.umc.momenty.domain.post.exception;

import com.umc.momenty.global.apiPayload.code.BaseErrorCode;
import com.umc.momenty.global.apiPayload.exception.GeneralException;

public class PostException extends GeneralException {
    public PostException(BaseErrorCode code) {super(code);
    }
}
