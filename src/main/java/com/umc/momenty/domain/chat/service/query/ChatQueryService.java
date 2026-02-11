package com.umc.momenty.domain.chat.service.query;

import java.util.List;

import com.umc.momenty.domain.chat.dto.req.ChatReqDTO;
import com.umc.momenty.domain.chat.dto.res.ChatResDTO;

public interface ChatQueryService {

    ChatResDTO.ChatResponse firstChat(Long userId, ChatReqDTO.ChatRequest chatRequest);
    ChatResDTO.ChatResponse chat(Long conversationId, ChatReqDTO.ChatRequest chatRequest);

    List<ChatResDTO.SearchChatDTO> searchChatList(Long userId, Long conversationId, String keyword);
}
