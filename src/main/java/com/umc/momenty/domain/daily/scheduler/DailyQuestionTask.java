package com.umc.momenty.domain.daily.scheduler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.umc.momenty.domain.daily.entity.DailyQuestion;
import com.umc.momenty.domain.daily.repository.DailyQuestionRepository;
import com.umc.momenty.global.infra.GeminiProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class DailyQuestionTask {

	@Value("${gemini.question.system-message}")
	private String systemMessage;

	@Value("${gemini.question.user-message}")
	private String userMessage;

	private final GeminiProvider geminiProvider;
	private final DailyQuestionRepository dailyQuestionRepository;

	@Transactional
	public Void createDailyQuestion() {
		String question = geminiProvider.generateTextContent(systemMessage, userMessage);

		DailyQuestion dailyQuestion = DailyQuestion.builder()
			.question(question)
			.build();
		dailyQuestionRepository.save(dailyQuestion);

		log.info("Daily Questions Saved Successfully [Question : {}]", question);
		return null;
	}
}
