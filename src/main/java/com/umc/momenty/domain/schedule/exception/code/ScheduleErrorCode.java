package com.umc.momenty.domain.schedule.exception.code;

import com.umc.momenty.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ScheduleErrorCode implements BaseErrorCode {

    // 404: Not Found
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "SCHEDULE404-1", "해당 일정을 찾을 수 없습니다."),

    // 403: Forbidden
    SCHEDULE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "SCHEDULE403-1", "해당 일정에 대한 접근 권한이 없습니다."),

    // 400: Bad Request
    INVALID_SCHEDULE_PERIOD(HttpStatus.BAD_REQUEST, "SCHEDULE400-1", "종료 날짜가 시작 날짜보다 앞설 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
