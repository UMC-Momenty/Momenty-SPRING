package com.umc.momenty.domain.pet.exception.code;

import com.umc.momenty.global.apiPayload.code.BaseSuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PetSuccessCode implements BaseSuccessCode {

    PET_FOUND(HttpStatus.OK, "PET200-1", "성공적으로 애완동물을 조회하였습니다."),
    PET_UPDATED(HttpStatus.OK, "PET200-2", "성공적으로 애완동물의 프로필을 수정하였습니다."),
    PET_CREATED(HttpStatus.CREATED, "PET201-1", "성공적으로 애완동물을 추가했습니다."),
    PET_DETAIL_FOUND(HttpStatus.OK, "PET200-3", "성공적으로 반려동물을 상세 조회하였습니다."),;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
