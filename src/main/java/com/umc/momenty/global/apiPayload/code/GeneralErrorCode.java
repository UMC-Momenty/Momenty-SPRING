package com.umc.momenty.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum GeneralErrorCode implements  BaseErrorCode {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400-1", "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401-1","인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403-1", "접근이 금지되었습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON404-1", "요청한 자원을 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500-1", "서버 내부 오류가 발생했습니다."),
    VALID_FAILED(HttpStatus.BAD_REQUEST, "COMMON400-2","검증에 실패했습니다."),
    GEMINI_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500-2","Gemini API 호출 중 오류 발생.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
