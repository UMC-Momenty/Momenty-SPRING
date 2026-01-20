package com.umc.momenty.domain.support.exception.code;

import com.umc.momenty.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum NoticeErrorCode implements BaseErrorCode {

    NOTICE_NOT_FOUND(HttpStatus.NOT_FOUND, "NOTICE404-1", "해당 공지사항울 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
