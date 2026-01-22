package com.umc.momenty.domain.daily.controller;

import com.umc.momenty.domain.daily.dto.res.DailyResDTO;
import com.umc.momenty.global.apiPayload.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface DailyControllerDocs {

	@Operation(summary = "오늘의 질문 조회 API", description = "오늘 날짜에 해당하는 질문을 조회합니다.")
	@ApiResponses({
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "실패")
	})
	ApiResponse<DailyResDTO.QuestionDTO> getTodayQuestion();
}
