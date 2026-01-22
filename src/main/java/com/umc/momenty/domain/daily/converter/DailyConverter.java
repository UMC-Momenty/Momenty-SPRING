package com.umc.momenty.domain.daily.converter;

import com.umc.momenty.domain.daily.dto.res.DailyResDTO;
import com.umc.momenty.domain.daily.entity.DailyAnswer;
import com.umc.momenty.domain.daily.entity.DailyQuestion;
import com.umc.momenty.domain.pet.entity.Pet;
import com.umc.momenty.domain.user.entity.User;

public class DailyConverter {

	public static DailyResDTO.QuestionDTO toQuestionDTO(DailyQuestion dailyQuestion) {
		return DailyResDTO.QuestionDTO.builder()
			.questId(dailyQuestion.getId())
			.date(dailyQuestion.getCreatedAt().toLocalDate())
			.quest(dailyQuestion.getQuestion())
			.build();
	}

	public static DailyAnswer toDailyAnswer(User user, Pet pet, DailyQuestion dailyQuestion, String answer) {
		return DailyAnswer.builder()
			.answer(answer)
			.user(user)
			.pet(pet)
			.dailyQuestion(dailyQuestion)
			.build();
	}
}
