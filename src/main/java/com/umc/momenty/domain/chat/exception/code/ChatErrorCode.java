package com.umc.momenty.domain.chat.exception.code;

import com.umc.momenty.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ChatErrorCode implements BaseErrorCode {

    CONVERSATION_CREATE_FAILED(HttpStatus.BAD_REQUEST, "CHAT400-1", "대화를 생성할 수 없습니다."),
    CONVERSATION_NOT_FOUND(HttpStatus.NOT_FOUND, "CHAT404-1", "존재하지 않는 대화입니다."),
    AI_RESPONSE_FAILED(HttpStatus.SERVICE_UNAVAILABLE, "CHAT503-1", "현재 AI 응답이 지연되고 있어요. 잠시 후 다시 시도해주세요."),
    NO_CONTEXT_AVAILABLE(HttpStatus.BAD_REQUEST, "CHAT400-2", "답변을 생성할 수 있는 정보가 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
