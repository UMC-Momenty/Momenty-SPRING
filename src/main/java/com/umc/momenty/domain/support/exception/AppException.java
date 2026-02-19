package com.umc.momenty.domain.support.exception;

import com.umc.momenty.global.apiPayload.code.BaseErrorCode;
import com.umc.momenty.global.apiPayload.exception.GeneralException;

public class AppException extends GeneralException {
  public AppException(BaseErrorCode code) {
    super(code);
  }
}
