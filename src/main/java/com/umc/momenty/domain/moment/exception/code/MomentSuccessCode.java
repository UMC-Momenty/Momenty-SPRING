package com.umc.momenty.domain.moment.exception.code;

import com.umc.momenty.global.apiPayload.code.BaseSuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MomentSuccessCode implements BaseSuccessCode {

    MOMENT_DETAIL_FOUND(HttpStatus.OK, "MOMENT200-1", "성공적으로 모먼트를 조회했습니다."),
    MOMENT_LIST_FOUND(HttpStatus.OK, "MOMENT200-2", "성공적으로 모먼트 리스트를 조회했습니다."),
    MOMENT_CREATED(HttpStatus.CREATED, "MOMENT201-1", "성공적으로 모먼트를 추가했습니다."),
    MOMENT_IMAGE_CREATED(HttpStatus.CREATED, "MOMENT201-2", "성공적으로 Presigned URL을 요청했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
