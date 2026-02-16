package com.umc.momenty.global.oauth.exception.code;

import com.umc.momenty.global.apiPayload.code.BaseSuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum OAuthSuccessCode implements BaseSuccessCode {

    OAUTH_LOGIN_SUCCESS(HttpStatus.OK, "OAUTH200-1", "OAuth 로그인에 성공하였습니다."),
    OAUTH_REISSUE_SUCCESS(HttpStatus.OK, "OAUTH200-3", "OAuth 토큰 재발급에 성공하였습니다."),
    OAUTH_LOGOUT_SUCCESS(HttpStatus.OK, "OAUTH200-2", "OAuth 로그아웃에 성공하였습니다.");


    private final HttpStatus status;
    private final String code;
    private final String message;

}
