package com.umc.momenty.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum GeneralSuccessCode implements BaseSuccessCode{

    OK(HttpStatus.OK,"COMMON200-1","요청이 성공적으로 처리되었습니다."),
    CREATED(HttpStatus.CREATED, "COMMON201-1", "요청이 성공적이었으며 새로운 리소스가 생성되었습니다."),
    ACCEPTED(HttpStatus.ACCEPTED, "COMMON202-1", "요청을 수신하였지만 처리가 완료되지 않았습니다."),
    NO_CONTENT(HttpStatus.NO_CONTENT, "COMMON204-1", "요청이 성공했으나 반환할 콘텐츠가 없습니다.");


    private final HttpStatus status;
    private final String code;
    private final String message;
}
