package com.umc.momenty.domain.daily.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

public class DailyReqDTO {

	@Builder
	public record AnswerDTO(
			Long questId,

			Long petId,

			@NotBlank(message = "답변 내용은 필수입니다.")
			String answer
	){}
}
