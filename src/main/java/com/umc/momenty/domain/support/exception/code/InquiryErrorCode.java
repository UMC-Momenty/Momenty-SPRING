package com.umc.momenty.domain.support.exception.code;

import com.umc.momenty.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum InquiryErrorCode implements BaseErrorCode {
    INQUIRY_NOT_FOUND(HttpStatus.NOT_FOUND, "INQUIRY404-1", "해당 문의내역을 찾을 수 없습니다."),
    PAGE_OUT_OF_RANGE(HttpStatus.BAD_REQUEST, "INQUIRY400-1", "요청한 페이지가 범위를 벗어났습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
