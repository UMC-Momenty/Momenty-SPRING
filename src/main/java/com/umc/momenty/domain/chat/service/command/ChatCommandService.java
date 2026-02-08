package com.umc.momenty.domain.chat.service.command;

import com.umc.momenty.domain.chat.entity.Conversation;

public interface ChatCommandService {

    Long saveBotChat(String answer, Conversation conversation);
    void saveUserChat(String question, Conversation conversation);
}
