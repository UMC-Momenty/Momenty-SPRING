package com.umc.momenty.domain.chat.service.query;

import com.umc.momenty.domain.chat.dto.res.ChatResDTO;

public interface ChatQueryService {

    ChatResDTO.ChatResponse chat(Long userId, String userMessage);
}
