package com.umc.momenty.domain.daily.exception.code;

import org.springframework.http.HttpStatus;

import com.umc.momenty.global.apiPayload.code.BaseSuccessCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DailySuccessCode implements BaseSuccessCode {

	QUESTION_FOUND(HttpStatus.OK, "DAILY200-1", "성공적으로 질문을 조회하였습니다."),
	ANSWER_CREATED(HttpStatus.CREATED, "DAILY201-1", "성공적으로 질문의 답변을 생성했습니다.");

	private final HttpStatus status;
	private final String code;
	private final String message;
}
