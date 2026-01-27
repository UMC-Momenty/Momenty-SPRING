package com.umc.momenty.domain.support.exception.code;

import com.umc.momenty.global.apiPayload.code.BaseSuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum InquirySuccessCode implements BaseSuccessCode {
    INQUIRY_DETAIL_FOUND(HttpStatus.OK, "INQUIRY200-1", "성공적으로 문의내역을 상세 조회하였습니다."),
    INQUIRY_LIST_FOUND(HttpStatus.OK, "INQUIRY200-2", "성공적으로 문의내역 목록을 조회하였습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
