package com.umc.momenty.domain.chat.service.query;

import java.util.List;

import com.umc.momenty.domain.chat.dto.res.ChatResDTO;

public interface ChatQueryService {

    ChatResDTO.ChatResponse firstChat(Long userId, String userMessage);
    ChatResDTO.ChatResponse chat(Long conversationId, String userMessage);

    List<ChatResDTO.SearchChatDTO> searchChatList(Long userId, Long conversationId, String keyword);
}
