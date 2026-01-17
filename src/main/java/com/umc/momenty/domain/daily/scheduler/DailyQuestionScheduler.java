package com.umc.momenty.domain.daily.scheduler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.umc.momenty.domain.daily.entity.DailyQuestion;
import com.umc.momenty.domain.daily.repository.DailyQuestionRepository;
import com.umc.momenty.global.infra.GeminiProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class DailyQuestionScheduler {

	@Value("${gemini.question.system-message}")
	private String systemMessage;

	@Value("${gemini.question.user-message}")
	private String userMessage;

	private final GeminiProvider geminiProvider;
	private final DailyQuestionRepository dailyQuestionRepository;

	// cron = "초 분 시 일 월 요일"
	// "0 0 0 * * *" -> 매일 0시 0분 0초 (자정)에 실행
	@Scheduled(cron = "0 0 0 * * *")
	@Transactional
	public void createDailyQuestion() {
		log.info("Creating Daily Question...");
		String question = geminiProvider.generateTextContent(systemMessage, userMessage);

		DailyQuestion dailyQuestion = DailyQuestion.builder()
			.question(question)
			.build();

		dailyQuestionRepository.save(dailyQuestion);
		log.info("Daily Questions Saved Successfully [Question : {}]", question);
	}
}
