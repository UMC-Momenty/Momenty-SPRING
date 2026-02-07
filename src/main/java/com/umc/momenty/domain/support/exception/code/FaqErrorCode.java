package com.umc.momenty.domain.support.exception.code;

import com.umc.momenty.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FaqErrorCode implements BaseErrorCode {

    FAQ_NOT_FOUND(HttpStatus.NOT_FOUND, "FAQ404-1", "해당 FAQ을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
