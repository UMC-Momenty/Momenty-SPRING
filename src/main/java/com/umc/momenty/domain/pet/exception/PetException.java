package com.umc.momenty.domain.pet.exception;

import com.umc.momenty.global.apiPayload.code.BaseErrorCode;
import com.umc.momenty.global.apiPayload.exception.GeneralException;

public class PetException extends GeneralException {
    public PetException(BaseErrorCode code) {
        super(code);
    }
}
