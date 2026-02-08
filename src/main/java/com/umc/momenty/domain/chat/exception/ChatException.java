package com.umc.momenty.domain.chat.exception;

import com.umc.momenty.global.apiPayload.code.BaseErrorCode;
import com.umc.momenty.global.apiPayload.exception.GeneralException;

public class ChatException extends GeneralException {
    public ChatException(BaseErrorCode code) {
        super(code);
    }
}
