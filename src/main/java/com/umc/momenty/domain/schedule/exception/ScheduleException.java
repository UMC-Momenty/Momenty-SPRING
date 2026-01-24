package com.umc.momenty.domain.schedule.exception;

import com.umc.momenty.global.apiPayload.exception.GeneralException;
import com.umc.momenty.global.apiPayload.code.BaseErrorCode;

public class ScheduleException extends GeneralException {

    public ScheduleException(BaseErrorCode code) {
        super(code);
    }
}
