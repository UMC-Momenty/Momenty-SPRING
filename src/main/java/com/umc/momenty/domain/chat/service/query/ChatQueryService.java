package com.umc.momenty.domain.chat.service.query;

import com.umc.momenty.domain.chat.dto.res.ChatResDTO;

public interface ChatQueryService {

    ChatResDTO.ChatResponse firstChat(Long userId, String userMessage);
    ChatResDTO.ChatResponse chat(Long conversationId, String userMessage);
}
