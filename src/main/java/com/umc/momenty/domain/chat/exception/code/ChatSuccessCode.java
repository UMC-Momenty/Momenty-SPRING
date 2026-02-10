package com.umc.momenty.domain.chat.exception.code;

import com.umc.momenty.global.apiPayload.code.BaseSuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ChatSuccessCode implements BaseSuccessCode {

    CHAT_CREATED(HttpStatus.CREATED, "CHAT201-1","성공적으로 대화를 추가했습니다."),
    CONVERSATION_FOUND(HttpStatus.OK, "CHAT-200-1", "성공적으로 채팅방을 조회했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
