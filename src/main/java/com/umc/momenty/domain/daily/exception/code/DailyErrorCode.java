package com.umc.momenty.domain.daily.exception.code;

import org.springframework.http.HttpStatus;

import com.umc.momenty.global.apiPayload.code.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DailyErrorCode implements BaseErrorCode {

	QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "DAILY404-1", "해당 질문을 찾을 수 없습니다."),
	EXIST_DAILY_ANSWER(HttpStatus.BAD_REQUEST, "DAILY400-1", "이미 답변한 질문입니다.");

	private final HttpStatus status;
	private final String code;
	private final String message;
}
