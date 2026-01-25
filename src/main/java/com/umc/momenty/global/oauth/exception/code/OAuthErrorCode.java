package com.umc.momenty.global.oauth.exception.code;

import com.umc.momenty.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum OAuthErrorCode implements BaseErrorCode {

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH401-1", "토큰이 유효하지 않습니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH401-2", "토큰이 만료되었습니다."),
    INVALID_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "AUTH401-3", "토큰 타입이 올바르지 않습니다."),

    REFRESH_TOKEN_MISSING(HttpStatus.UNAUTHORIZED, "AUTH401-4", "리프레시 토큰이 존재하지 않습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH401-5", "리프레시 토큰이 유효하지 않습니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH401-6", "리프레시 토큰이 만료되었습니다."),
    REFRESH_TOKEN_MISMATCH(HttpStatus.UNAUTHORIZED, "AUTH401-7", "리프레시 토큰이 일치하지 않습니다."),
    OAUTH_PROVIDER_REQUIRED(HttpStatus.BAD_REQUEST, "AUTH400-1","OAuth 제공자가 필요합니다."),
    OAUTH_PROVIDER_UNSUPPORTED(HttpStatus.BAD_REQUEST, "AUTH400-2","지원되지 않는 OAuth 제공자입니다."),
    OAUTH_USERINFO_FAILED(HttpStatus.BAD_GATEWAY,"AUTH502-1 " ,"OAuth 유저 정보 불러오기 실패했습니다."),
    OAUTH_EMAIL_ALREADY_USED(HttpStatus.CONFLICT, "AUTH409-1", "해당 이메일로 가입된 다른 소셜 계정이 존재합니다."),

    FORBIDDEN(HttpStatus.FORBIDDEN, "AUTH403-1", "접근이 금지되었습니다. 프로필 입력을 완료해주세요.");




    private final HttpStatus status;
    private final String code;
    private final String message;





}
