package com.umc.momenty.domain.pet.exception.code;

import com.umc.momenty.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PetErrorCode implements BaseErrorCode {

    PET_NOT_FOUND(HttpStatus.NOT_FOUND, "PET404-1", "해당 애완동물을 찾을 수 없습니다."),
    BREED_NOT_FOUND(HttpStatus.NOT_FOUND, "PET404-2", "해당 품종을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
