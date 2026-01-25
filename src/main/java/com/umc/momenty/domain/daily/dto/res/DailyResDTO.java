package com.umc.momenty.domain.daily.dto.res;

import java.time.LocalDate;

import lombok.Builder;

public class DailyResDTO {

	@Builder
	public record QuestionDTO (
			Long questId,
			LocalDate date,
			String quest
	){}

	@Builder
	public record QuestionAnswerDTO(
			Long questId,
			Long answerId,
			LocalDate date,
			String quest,
			String answer
	){}
}
