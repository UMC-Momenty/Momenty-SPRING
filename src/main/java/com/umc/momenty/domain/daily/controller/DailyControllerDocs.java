package com.umc.momenty.domain.daily.controller;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;

import com.umc.momenty.domain.daily.dto.req.DailyReqDTO;
import com.umc.momenty.domain.daily.dto.res.DailyResDTO;
import com.umc.momenty.global.annotation.AuthUser;
import com.umc.momenty.global.apiPayload.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

public interface DailyControllerDocs {

	@Operation(summary = "오늘의 질문 조회 API", description = "오늘 날짜에 해당하는 질문을 조회합니다.")
	@ApiResponses({
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "실패")
	})
	ApiResponse<DailyResDTO.QuestionDTO> getTodayQuestion();

	@Operation(summary = "질문 답변 작성 API", description = "ID에 해당하는 질문의 답변을 생성합니다.")
	@ApiResponses({
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "성공"),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패"),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 질문")
	})
	ApiResponse<Void> createDailyAnswer(
		@AuthUser
		Long userId,

		@RequestBody(
			description = """
				프로필 추가 내용
				**요청 예시:**
				- questId: 21
				- petId: 3
				- answer: "날씨가 맑아 기분이 좋았다"
				""",
			required = true
		)
		@Valid DailyReqDTO.AnswerDTO answerDTO
	);

	@Operation(summary = "질문 답변 조회 API", description = "기간 안에 해당되는 질문들의 답변을 조회합니다.")
	@ApiResponses({
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패"),
	})
	ApiResponse<List<DailyResDTO.QuestionAnswerDTO>> getAnswerList(
		@AuthUser
		Long userId,

		@ParameterObject
		@Valid DailyReqDTO.DateRangeDTO dateRangeDTO
	);

	@Operation(summary = "질문 답변 작성 여부 API", description = "오늘의 질문에 답변한 상태인지 조회합니다.")
	@ApiResponses({
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패"),
	})
	ApiResponse<DailyResDTO.AnswerStatusDTO> getAnswerList(
		@AuthUser
		Long userId
	);
}
