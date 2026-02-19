package com.umc.momenty.domain.moment.exception.code;

import com.umc.momenty.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MomentErrorCode implements BaseErrorCode {

    MOMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "MOMENT404-1", "해당 모먼트를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

}
