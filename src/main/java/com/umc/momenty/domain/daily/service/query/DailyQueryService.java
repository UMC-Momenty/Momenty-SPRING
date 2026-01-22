package com.umc.momenty.domain.daily.service.query;

import com.umc.momenty.domain.daily.dto.res.DailyResDTO;

public interface DailyQueryService {
	DailyResDTO.QuestionDTO getTodayQuestion();
}
