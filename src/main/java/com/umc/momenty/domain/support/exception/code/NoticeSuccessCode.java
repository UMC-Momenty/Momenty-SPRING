package com.umc.momenty.domain.support.exception.code;

import com.umc.momenty.global.apiPayload.code.BaseSuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum NoticeSuccessCode implements BaseSuccessCode {

    NOTICE_DETAIL_FOUND(HttpStatus.OK, "NOTICE200-1", "성공적으로 공지사항을 상세 조회하였습니다."),
    NOTICE_LIST_FOUND(HttpStatus.OK, "NOTICE200-2", "성공적으로 공지사항 목록을 조회하였습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
