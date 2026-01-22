package com.umc.momenty.domain.daily.service.query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.umc.momenty.domain.daily.converter.DailyConverter;
import com.umc.momenty.domain.daily.dto.res.DailyResDTO;
import com.umc.momenty.domain.daily.entity.DailyQuestion;
import com.umc.momenty.domain.daily.exception.DailyException;
import com.umc.momenty.domain.daily.exception.code.DailyErrorCode;
import com.umc.momenty.domain.daily.repository.DailyQuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DailyQueryServiceImpl implements DailyQueryService {

	private final DailyQuestionRepository questionRepository;

	@Override
	@Transactional(readOnly = true)
	public DailyResDTO.QuestionDTO getTodayQuestion() {
		LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
		LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);

		DailyQuestion dailyQuestion = questionRepository.findFirstByCreatedAtBetween(startOfDay, endOfDay)
			.orElseThrow(() -> new DailyException(DailyErrorCode.QUESTION_NOT_FOUND));

		return DailyConverter.toQuestionDTO(dailyQuestion);
	}
}
