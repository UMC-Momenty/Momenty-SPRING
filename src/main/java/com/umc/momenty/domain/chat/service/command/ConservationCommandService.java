package com.umc.momenty.domain.chat.service.command;

import com.umc.momenty.domain.chat.entity.Conversation;

public interface ConservationCommandService {

    Conversation createConversation(Long userId, String firstMessage);
}
