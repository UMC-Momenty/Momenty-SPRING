package com.umc.momenty.domain.daily.dto.req;

import java.time.LocalDate;

import com.umc.momenty.domain.daily.annotation.ValidDateRange;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;

public class DailyReqDTO {

	@Builder
	public record AnswerDTO(
			Long questId,

			Long petId,

			@NotBlank(message = "답변 내용은 필수입니다.")
			String answer
	){}

	@Builder
	@ValidDateRange
	public record DateRangeDTO(
			@Schema(
				description = "조회 시작 날짜 (오늘 또는 과거)",
				example = "2026-01-01"
			)
			@PastOrPresent
			LocalDate from,

			@Schema(
				description = "조회 종료 날짜 (오늘 또는 과거)",
				example = "2024-01-31"
			)
			@PastOrPresent
			LocalDate to
	){}
}
