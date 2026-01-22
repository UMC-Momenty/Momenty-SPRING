package com.umc.momenty.domain.daily.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.umc.momenty.domain.daily.dto.res.DailyResDTO;
import com.umc.momenty.domain.daily.exception.code.DailySuccessCode;
import com.umc.momenty.domain.daily.service.query.DailyQueryService;
import com.umc.momenty.global.apiPayload.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DailyController implements DailyControllerDocs{

	private final DailyQueryService dailyQueryService;

	@GetMapping("/quest/today")
	@Override
	public ApiResponse<DailyResDTO.QuestionDTO> getTodayQuestion() {
		return ApiResponse.onSuccess(DailySuccessCode.QUESTION_FOUND, dailyQueryService.getTodayQuestion());
	}
}
