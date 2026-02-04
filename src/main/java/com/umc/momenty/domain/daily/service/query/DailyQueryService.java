package com.umc.momenty.domain.daily.service.query;

import java.util.List;

import com.umc.momenty.domain.daily.dto.req.DailyReqDTO;
import com.umc.momenty.domain.daily.dto.res.DailyResDTO;

public interface DailyQueryService {
	DailyResDTO.QuestionDTO getTodayQuestion();

	List<DailyResDTO.QuestionAnswerDTO> getAnswerList(Long userId, DailyReqDTO.DateRangeDTO dateRangeDTO);

	DailyResDTO.AnswerStatusDTO getTodayAnswerStatus(Long userId);
}
