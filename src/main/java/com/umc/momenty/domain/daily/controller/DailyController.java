package com.umc.momenty.domain.daily.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.umc.momenty.domain.daily.dto.req.DailyReqDTO;
import com.umc.momenty.domain.daily.dto.res.DailyResDTO;
import com.umc.momenty.domain.daily.exception.code.DailySuccessCode;
import com.umc.momenty.domain.daily.service.command.DailyCommandService;
import com.umc.momenty.domain.daily.service.query.DailyQueryService;
import com.umc.momenty.global.apiPayload.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DailyController implements DailyControllerDocs{

	private final DailyQueryService dailyQueryService;
	private final DailyCommandService dailyCommandService;

	@GetMapping("/quest/today")
	@Override
	public ApiResponse<DailyResDTO.QuestionDTO> getTodayQuestion() {
		return ApiResponse.onSuccess(DailySuccessCode.QUESTION_FOUND, dailyQueryService.getTodayQuestion());
	}

	@PostMapping("/users/{userId}/answers")
	@Override
	public ApiResponse<Void> createDailyAnswer(
		@PathVariable Long userId,
		@Valid @RequestBody DailyReqDTO.AnswerDTO answerDTO
	) {
		dailyCommandService.createDailyAnswer(userId, answerDTO);
		return ApiResponse.onSuccess(DailySuccessCode.ANSWER_CREATED, null);
	}

	@GetMapping("/users/{userId}/answers")
	@Override
	public ApiResponse<List<DailyResDTO.QuestionAnswerDTO>> getAnswerList(
		@PathVariable Long userId,
		@Valid DailyReqDTO.DateRangeDTO dateRangeDTO
	) {
		return ApiResponse.onSuccess(DailySuccessCode.ANSWER_FOUND, dailyQueryService.getAnswerList(userId, dateRangeDTO));
	}

	@GetMapping("/users/{userId}/quest/today/status")
	@Override
	public ApiResponse<DailyResDTO.AnswerStatusDTO> getAnswerList(
		@PathVariable Long userId
	) {
		return ApiResponse.onSuccess(DailySuccessCode.ANSWER_STATUS_FOUND, dailyQueryService.getTodayAnswerStatus(userId));
	}
}
