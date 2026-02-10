package com.umc.momenty.domain.post.exception.code;

import com.umc.momenty.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PostErrorCode implements BaseErrorCode {
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST404-1", "게시글을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
