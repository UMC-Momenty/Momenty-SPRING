package com.umc.momenty.domain.daily.exception;

import com.umc.momenty.global.apiPayload.code.BaseErrorCode;
import com.umc.momenty.global.apiPayload.exception.GeneralException;

public class DailyException extends GeneralException {
	public DailyException(BaseErrorCode code) {
		super(code);
	}
}
