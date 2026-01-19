package com.umc.momenty.domain.pet.exception.code;

import com.umc.momenty.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PetErrorCode implements BaseErrorCode {

    PET_NOT_FOUND(HttpStatus.NOT_FOUND, "PET404-1", "해당 애완동물을 찾을 수 없습니다."),
    BREED_NOT_FOUND(HttpStatus.NOT_FOUND, "PET404-2", "해당 품종을 찾을 수 없습니다."),
    PET_ACCESS_DENIED(HttpStatus.FORBIDDEN, "PET403-1", "해당 반려동물에 대한 접근 권한이 없습니다."),
    INVALID_BREED_SPECIES(HttpStatus.BAD_REQUEST, "PET400-1", "선택한 품종이 반려동물 종류와 일치하지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
