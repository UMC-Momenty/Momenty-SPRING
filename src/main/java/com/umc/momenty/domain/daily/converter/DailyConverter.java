package com.umc.momenty.domain.daily.converter;

import com.umc.momenty.domain.daily.dto.res.DailyResDTO;
import com.umc.momenty.domain.daily.entity.DailyQuestion;

public class DailyConverter {

	public static DailyResDTO.QuestionDTO toQuestionDTO(DailyQuestion dailyQuestion) {
		return DailyResDTO.QuestionDTO.builder()
			.questId(dailyQuestion.getId())
			.date(dailyQuestion.getCreatedAt().toLocalDate())
			.quest(dailyQuestion.getQuestion())
			.build();
	}
}
