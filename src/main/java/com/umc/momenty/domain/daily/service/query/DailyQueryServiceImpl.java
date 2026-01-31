package com.umc.momenty.domain.daily.service.query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.umc.momenty.domain.daily.converter.DailyConverter;
import com.umc.momenty.domain.daily.dto.req.DailyReqDTO;
import com.umc.momenty.domain.daily.dto.res.DailyResDTO;
import com.umc.momenty.domain.daily.entity.DailyAnswer;
import com.umc.momenty.domain.daily.entity.DailyQuestion;
import com.umc.momenty.domain.daily.exception.DailyException;
import com.umc.momenty.domain.daily.exception.code.DailyErrorCode;
import com.umc.momenty.domain.daily.repository.DailyAnswerRepository;
import com.umc.momenty.domain.daily.repository.DailyQuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DailyQueryServiceImpl implements DailyQueryService {

	private final DailyQuestionRepository questionRepository;
	private final DailyAnswerRepository answerRepository;

	@Override
	@Transactional(readOnly = true)
	public DailyResDTO.QuestionDTO getTodayQuestion() {
		LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
		LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);

		DailyQuestion dailyQuestion = questionRepository.findFirstByCreatedAtBetween(startOfDay, endOfDay)
			.orElseThrow(() -> new DailyException(DailyErrorCode.QUESTION_NOT_FOUND));

		return DailyConverter.toQuestionDTO(dailyQuestion);
	}

	@Override
	public List<DailyResDTO.QuestionAnswerDTO> getAnswerList(Long userId, DailyReqDTO.DateRangeDTO dateRangeDTO) {
		LocalDateTime startOfDay = dateRangeDTO.from().atStartOfDay();
		LocalDateTime endOfDay = dateRangeDTO.to().atTime(LocalTime.MAX);

		List<DailyAnswer> dailyAnswers = answerRepository.findAllByUserIdAndCreatedAtBetween(userId, startOfDay, endOfDay);
		return dailyAnswers.stream()
			.map(DailyConverter::toQuestionAnswerDTO)
			.toList();
	}
}
