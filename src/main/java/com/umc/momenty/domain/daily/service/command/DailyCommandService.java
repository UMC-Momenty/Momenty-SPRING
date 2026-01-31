package com.umc.momenty.domain.daily.service.command;

import com.umc.momenty.domain.daily.dto.req.DailyReqDTO;

public interface DailyCommandService {
	void createDailyAnswer(Long userId, DailyReqDTO.AnswerDTO answerDTO);
}
