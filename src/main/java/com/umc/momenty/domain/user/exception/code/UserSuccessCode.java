package com.umc.momenty.domain.user.exception.code;

import com.umc.momenty.global.apiPayload.code.BaseSuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserSuccessCode implements BaseSuccessCode {

    USER_FOUND(HttpStatus.OK, "MEMBER200-1", "성공적으로 사용자를 조회하였습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
