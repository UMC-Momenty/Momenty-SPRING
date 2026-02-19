package com.umc.momenty.domain.chat.service.command;

import com.umc.momenty.domain.chat.dto.req.ChatReqDTO;
import com.umc.momenty.domain.chat.entity.Conversation;

public interface ChatCommandService {

    Long saveBotChat(String answer, Conversation conversation);
    Long saveUserChat(ChatReqDTO.ChatRequest question, Conversation conversation);
}
