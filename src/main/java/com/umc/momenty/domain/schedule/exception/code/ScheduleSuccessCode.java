package com.umc.momenty.domain.schedule.exception.code;

import com.umc.momenty.global.apiPayload.code.BaseSuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ScheduleSuccessCode implements BaseSuccessCode {

    SCHEDULE_FOUND(HttpStatus.OK, "SCHEDULE200-1", "성공적으로 일정을 조회하였습니다."),
    SCHEDULE_UPDATED(HttpStatus.OK, "SCHEDULE200-2", "성공적으로 일정을 수정하였습니다."),
    SCHEDULE_DELETED(HttpStatus.OK, "SCHEDULE200-3", "성공적으로 일정을 삭제하였습니다."),
    SCHEDULE_CREATED(HttpStatus.CREATED, "SCHEDULE201-1", "성공적으로 일정을 등록했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
