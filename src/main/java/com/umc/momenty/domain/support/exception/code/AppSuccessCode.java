package com.umc.momenty.domain.support.exception.code;

import com.umc.momenty.global.apiPayload.code.BaseSuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AppSuccessCode implements BaseSuccessCode {
    APP_DETAIL_FOUND(HttpStatus.OK, "APP200-1", "성공적으로 앱 정보를 상세 조회하였습니다."),
    APP_LIST_FOUND(HttpStatus.OK, "APP200-2", "성공적으로 앱 정보 목록을 조회하였습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
